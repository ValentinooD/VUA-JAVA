package hr.algebra.rss;

import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Movie;
import hr.algebra.utilities.FileUtils;
import hr.algebra.utilities.NumberUtils;
import hr.algebra.utilities.web.UrlConnectionFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
    
    public static List<Movie> getItems(String url) throws Exception {
        HttpURLConnection conn = UrlConnectionFactory.getHttpUrlConnection(RSS_URL);
        List<Movie> list = new ArrayList<>();
        
        try (InputStream is = conn.getInputStream()) {
            
            Optional<TagType> tagType = Optional.empty();
            XMLEventReader reader = XMLInputFactory.newFactory().createXMLEventReader(is);
            StartElement startElement = null;
            Movie item = null;
            
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                switch (event.getEventType()) {
                    case XMLStreamConstants.START_ELEMENT -> {
                        startElement = event.asStartElement();
                        String qName = startElement.getName().getLocalPart();
                        tagType = TagType.from(qName);
                        // put breakpoint here
                        if (tagType.isPresent() && tagType.get().equals(TagType.ITEM)) {
                            item = new Movie();
                            list.add(item);
                        }
                    }
                    case XMLStreamConstants.CHARACTERS -> {
                        if (item == null) continue;
                        
                        if (tagType.isPresent()) {
                            Characters characters = event.asCharacters();
                            String data = characters.getData().trim();
                            
                            switch (tagType.get()) {
                                case TITLE -> {
                                    if (!data.isEmpty()) {
                                        item.setTitle(data);
                                    }
                                }
                                case LINK -> {
                                    if (!data.isEmpty()) {
                                        item.setLink(data);
                                    }
                                }
                                case DESCRIPTION -> {
                                    if (!data.isEmpty()) {
                                        item.setDescription(data);
                                    }
                                }
                                case PUB_DATE -> {
                                    if (!data.isEmpty()) {
                                        LocalDateTime ldt = LocalDateTime.parse(data, DateTimeFormatter.RFC_1123_DATE_TIME);
                                        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
                                        
                                        item.setPubishDate(date);
                                    }
                                }
                                case ACTORS -> {
                                    if (!data.isEmpty()) {
                                        List<Actor> actorsList = new ArrayList<>();
                                        String[] l = data.split(", ");
                                        
                                        for (String a : l) {
                                            String[] s = data.split(" ");
                                            actorsList.add(new Actor(s[0], s[1]));
                                        }
                                        
                                        item.setActors(actorsList);
                                    }
                                }
                                case DIRECTOR -> {
                                    if (!data.isEmpty()) {
                                        String[] s = data.split(" ");
                                        
                                        Director director = new Director(s[0], s[1]);
                                        item.setDirector(director);
                                    }
                                }
                                case SHOWING_DATE -> {
                                    if (!data.isEmpty()) {
                                        item.setShowingDate(DATE_FORMAT.parse(data));
                                    }
                                }
                                case PICTURE -> {
                                    if (!data.isEmpty()) {
                                        handlePicture(item, data);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
        return list;
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
        
        TITLE("title"),
        DESCRIPTION("description"),
        LINK("link"),
        PUB_DATE("pubDate"),
        ORIGINAL_NAME("orignaziv"),
        PICTURE("plakat"),
        DIRECTOR("redatelj"),
        ACTORS("glumci"),
        DURATION("trajanje"),
        YEAR("godina"),
        GENRE("zanr"),
        TYPE("vrsta"),
        SHOWING_DATE("datumprikazivanja"),
        SORT("sort"),
        TRAILER("trailer")
        ;
        
        private final String name;

        private TagType(String name) {
            this.name = name;
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
