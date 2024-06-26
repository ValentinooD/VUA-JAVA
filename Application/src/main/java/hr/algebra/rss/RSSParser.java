package hr.algebra.rss;

import hr.algebra.model.Movie;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.web.UrlConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;


public class RSSParser {
    private static final String RSS_URL = "https://www.blitz-cinestar-bh.ba/rss.aspx?id=2682";
    private static final String EXT = ".jpg";
    private static final String DIR = "assets";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
    
    public static List<Movie> getItems() throws Exception {
        HttpURLConnection conn = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        List<Movie> list = new ArrayList<>();
        
        try (InputStream is = conn.getInputStream()) {
            
            Optional<TagType> tagType = Optional.empty();
            XMLEventReader reader = XMLInputFactory.newFactory().createXMLEventReader(is);
            StartElement startElement = null;
            Movie movie = null;
            
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        // put breakpoint here
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            movie = new Movie();
                            list.add(movie);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (movie == null) continue;
                        
                        if (tagType.isPresent() && tagType.get().getFieldName() != null) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            
                            Type type = movie.getClass().getDeclaredField(tagType.get().getFieldName())
                                    .getGenericType();

                            if (type instanceof ParameterizedType) {
                                type = ((ParameterizedType) type).getRawType();
                            }
                            
                            if (tagType.get() == TagType.PICTURE) {
                                if (!data.isEmpty()) {
                                    handlePicture(movie, data);
                                }
                                continue;
                            }
                            
                            if (type == String.class) {
                                setValue(movie, tagType.get(), data);
                            } else if (type == Date.class) {
                                Date date;
                                if (tagType.get() == TagType.SHOWING_DATE) {
                                    date = DATE_FORMAT.parse(data);
                                } else {
                                    LocalDateTime ldt = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                    date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
                                }
                                
                                setValue(movie, tagType.get(), date);
                            } else if (type == Set.class) {
                                Type paramType = ((ParameterizedType) movie.getClass().getDeclaredField(tagType.get().getFieldName()).getGenericType())
                                        .getActualTypeArguments()[0];
                                
                                Class<?> clazz = Class.forName(paramType.getTypeName());
                                
                                if (data.isEmpty()) continue;
                                
                                Set<Object> set = new HashSet<>();
                                String[] nameList = data.split(", ");
                                for (String name : nameList) {
                                    String[] names = name.split(" ");
                                    
                                    Object obj = clazz.getDeclaredConstructor(String.class, String.class)
                                            .newInstance(names[0], names[1]);
                                    
                                    set.add(obj);
                                }
                                
                                setValue(movie, tagType.get(), set);
                            }
                        }
                    }

                }
            }
        }
        
        return list;
    }
    
    private static void setValue(Object obj, TagType tagType, Object value) {
        try {
            Field field;
            field = obj.getClass().getDeclaredField(tagType.getFieldName());
            field.setAccessible(true);
            field.set(obj, value);
            
        } catch (Exception ex) {
            Logger.getLogger(RSSParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
   
    private static void handlePicture(Movie item, String pictureUrl) {
        try {
            String ext = pictureUrl.substring(pictureUrl.lastIndexOf("."));
            if (ext.length() > 4) {
                ext = EXT;
            }
            String pictureName = UUID.randomUUID() + ext;
            String localPicturePath = DIR + File.separator + pictureName;

            FileUtils.copyFromUrl(pictureUrl, localPicturePath);
            item.setBannerPath(localPicturePath);
        } catch (IOException ex) {
            Logger.getLogger(RSSParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private enum TagType {
        ITEM("item"),
        
        
        TITLE("title", "title"),
        DESCRIPTION("description", "description"),
        DIRECTOR("redatelj", "directors"),
        ACTORS("glumci", "actors"),
        
        PICTURE("plakat", "bannerPath"),
        LINK("link", "link"),
        
        PUB_DATE("pubDate", "publishDate"),
        SHOWING_DATE("datumprikazivanja", "showingDate");
        
        private final String name;
        private final String fieldName;

        private TagType(String name) {
            this.name = name;
            this.fieldName = null;
        }

        private TagType(String name, String fieldName) {
            this.name = name;
            this.fieldName = fieldName;
        }

        public String getFieldName() {
            return fieldName;
        }

        private static Optional<TagType> from(String name) {
            for (TagType value : values()) {
                if (value.name.equals(name)) {
                    return Optional.of(value);
                }
            }
            return Optional.empty();
        }
    }
}
