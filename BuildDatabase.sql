CREATE DATABASE JAVA;
GO

USE JAVA;
GO

-- USERS
CREATE TABLE Users (
	IDUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(100),
	Password NVARCHAR(100),
	Role NVARCHAR(20) DEFAULT 'USER'
);
GO

CREATE OR ALTER PROC createUser
(
	@Username NVARCHAR(100),
	@Password NVARCHAR(100),
	@Role NVARCHAR(20) = 'USER',
	@Success BIT OUT,
	@IDUser INT OUT
)
AS
BEGIN
	IF EXISTS(SELECT * FROM Users WHERE Username=@Username)
	BEGIN
		SET @Success = 0
		RETURN
	END

	INSERT INTO Users (Username, Password, Role)
		VALUES (@Username, @Password, @Role)

	SET @IDUser = SCOPE_IDENTITY()
	set @Success = 1
END
GO

CREATE OR ALTER PROC updateUser
(
	@IDUser INT,
	@Username NVARCHAR(100) = NULL,
	@Password NVARCHAR(100) = NULL,
	@Role NVARCHAR(20) = NULL,
	@Success BIT OUT
)
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Users WHERE IDUser=@IDUser)
	begin
		SET @Success = 0
		RETURN
	end
	
	IF @Username IS NOT NULL
	BEGIN
		IF EXISTS(SELECT * FROM Users WHERE Username=@Username)
		BEGIN
			SET @Success = 0
		END
	
		UPDATE Users SET Username=@Username WHERE IDUser=@IDUser
	END


	IF @Password IS NOT NULL
		UPDATE Users SET Password=@Password WHERE IDUser=@IDUser

	IF @Role IS NOT NULL
		UPDATE Users SET Role=@Role WHERE IDUser=@IDUser
		
	set @Success = 1
END
GO

CREATE OR ALTER PROC deleteUser
(
	@IDUser INT,
	@Success BIT OUT
)
AS
BEGIN
	DELETE FROM Users WHERE IDUser=@IDUser
	set @Success = 1
END
GO

CREATE OR ALTER PROCEDURE selectUser
	@IDUser INT
AS 
BEGIN 
	SELECT 
		* 
	FROM 
		Users
	WHERE 
		IDUser = @IDUser
END
GO

CREATE OR ALTER PROCEDURE selectUsers
AS 
BEGIN 
	SELECT * FROM Users
END
GO

CREATE OR ALTER PROCEDURE authenticateUser
	@Username NVARCHAR(100),
	@Password NVARCHAR(100)
AS
BEGIN
	SELECT * FROM Users WHERE Username=@Username AND Password=@Password
END
GO

-- actors
CREATE TABLE Actors (
	IDActor INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(100),
	LastName NVARCHAR(100)
);
GO

CREATE OR ALTER PROC selectActor
(
	@IDActor INT
)
AS
BEGIN
	SELECT * FROM Actors WHERE IDActor=@IDActor
END
GO

CREATE OR ALTER PROC selectActors 
AS
BEGIN
	SELECT * FROM Actors
END
GO

CREATE OR ALTER PROC updateActor
(
	@IDActor INT,
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@Success BIT OUT
)
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Actors WHERE IDActor=@IDActor)
	begin
		SET @Success = 0
		RETURN
	end
	
	IF @FirstName IS NOT NULL
		UPDATE Actors SET FirstName=@FirstName WHERE IDActor=@IDActor

	IF @LastName IS NOT NULL
		UPDATE Actors SET LastName=LastName WHERE IDActor=@IDActor

	set @Success = 1
END
GO

CREATE OR ALTER PROC createActor
(
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@IDActor INT OUT
)
AS
BEGIN
	IF EXISTS(SELECT IDActor=@IDActor FROM Actors WHERE FirstName=@FirstName AND LastName=@LastName)
	BEGIN
		SET @IDActor = (SELECT IDActor FROM Actors WHERE FirstName=@FirstName AND LastName=@LastName)
		RETURN
	END

	INSERT INTO Actors (FirstName, LastName) VALUES (@FirstName, @LastName);
	SET @IDActor = SCOPE_IDENTITY()
END
GO

CREATE OR ALTER PROC deleteActor
(
	@IDActor INT
)
AS
BEGIN
	DELETE FROM Actors WHERE IDActor = @IDActor
END
GO

-- directors
CREATE TABLE Directors (
	IDDirector INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(100),
	LastName NVARCHAR(100)
);
GO

CREATE OR ALTER PROC selectDirector
(
	@IDDirector INT
)
AS
BEGIN
	SELECT * FROM Directors WHERE IDDirector=@IDDirector
END
GO

CREATE OR ALTER PROC selectDirectors
AS
BEGIN
	SELECT * FROM Directors
END
GO

CREATE OR ALTER PROC updateDirector
(
	@IDDirector INT,
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@Success BIT OUT
)
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Directors WHERE IDDirector=@IDDirector)
	begin
		SET @Success = 0
		RETURN
	end
	
	IF @FirstName IS NOT NULL
		UPDATE Directors SET FirstName=@FirstName WHERE IDDirector=@IDDirector

	IF @LastName IS NOT NULL
		UPDATE Directors SET LastName=LastName WHERE IDDirector=@IDDirector

	set @Success = 1
END
GO

CREATE OR ALTER PROC createDirector
(
	@FirstName NVARCHAR(100),
	@LastName NVARCHAR(100),
	@IDDirector INT OUT
)
AS
BEGIN
	INSERT INTO Directors(FirstName, LastName) VALUES (@FirstName, @LastName);
	SET @IDDirector = SCOPE_IDENTITY()
END
GO

CREATE OR ALTER PROC deleteDirector
(
	@IDDirector INT
)
AS
BEGIN
	DELETE FROM Directors WHERE IDDirector = @IDDirector
END
GO

-- movies
CREATE TABLE Movies (
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(200),
	Description NVARCHAR(500),
	BannerPath NVARCHAR(200),
	Link NVARCHAR(300),
	PublishDate DATE,
	ShowingDate DATE
);
GO

CREATE OR ALTER PROC createMovie
(
	@Title NVARCHAR(200),
	@Description NVARCHAR(500),
	@BannerPath NVARCHAR(200),
	@Link NVARCHAR(300),
	@PublishDate DATE,
	@ShowingDate DATE,
	@IDMovie INT OUT,
	@Success INT OUT
)
AS
BEGIN
	SET @Success = 0

	INSERT INTO Movies (Title, Description, BannerPath, Link, PublishDate, ShowingDate)
		VALUES (@Title, @Description, @BannerPath, @Link, @PublishDate, @ShowingDate);

	SET @IDMovie = SCOPE_IDENTITY()
	SET @Success = 1
END
GO

CREATE OR ALTER PROC updateMovie
(
	@IDMovie INT,
	@Title NVARCHAR(200),
	@Description NVARCHAR(500),
	@BannerPath NVARCHAR(200),
	@Link NVARCHAR(300),
	@PublishDate DATE,
	@ShowingDate DATE,
	@Success INT OUT
)
AS
BEGIN
	IF NOT EXISTS(SELECT * FROM Movies WHERE IDMovie=@IDMovie)
	begin
		SET @Success = 0
		RETURN
	end
	
	IF @Title IS NOT NULL
		UPDATE Movies SET Title=@Title WHERE IDMovie=@IDMovie
	
	IF @Description IS NOT NULL
		UPDATE Movies SET Description=@Description WHERE IDMovie=@IDMovie
	
	IF @BannerPath IS NOT NULL
		UPDATE Movies SET BannerPath=@BannerPath WHERE IDMovie=@IDMovie
	
	IF @Link IS NOT NULL
		UPDATE Movies SET Link=@Link WHERE IDMovie=@IDMovie
	
	IF @PublishDate IS NOT NULL
		UPDATE Movies SET PublishDate=@PublishDate WHERE IDMovie=@IDMovie
	
	IF @ShowingDate IS NOT NULL
		UPDATE Movies SET ShowingDate=@ShowingDate WHERE IDMovie=@IDMovie

	set @Success = 1
END
GO

CREATE OR ALTER PROC deleteMovie
(
	@IDMovie INT
)
AS
BEGIN
	DELETE FROM ActorsMoviesRelationship WHERE MovieID=@IDMovie
	DELETE FROM DirectorsMoviesRelationship WHERE MovieID=@IDMovie

	DELETE FROM Movies WHERE IDMovie=@IDMovie
END
GO

CREATE OR ALTER PROC selectMovie
(
	@IDMovie INT
)
AS
BEGIN
	SELECT * FROM Movies WHERE IDMovie=@IDMovie
END
GO

CREATE OR ALTER PROC selectMovies
AS
BEGIN
	SELECT * FROM Movies
END
GO

-- relationship
CREATE TABLE ActorsMoviesRelationship (
	ActorID INT,
	MovieID INT,
	FOREIGN KEY (ActorID) REFERENCES Actors(IDActor),
	FOREIGN KEY (MovieID) REFERENCES Movies(IDMovie)
)
GO

CREATE OR ALTER PROC selectActorsForMovie
(
	@MovieID INT
)
AS
BEGIN
	SELECT * FROM Actors WHERE IDActor 
		IN (SELECT ActorID FROM ActorsMoviesRelationship WHERE MovieID=@MovieID)
END
GO

CREATE OR ALTER PROC addActorToMovie
(
	@ActorID INT,
	@MovieID INT,
	@Success INT OUT
)
AS 
BEGIN
	SET @Success = 0

	IF EXISTS(SELECT * FROM ActorsMoviesRelationship WHERE ActorID=@ActorID AND MovieID=@MovieID)
	BEGIN
		SET @Success = 1 -- it's as if it succeeded
		RETURN
	END
	
	IF NOT EXISTS(SELECT * FROM Movies WHERE IDMovie=@MovieID)
	BEGIN
		RETURN
	END

	IF NOT EXISTS(SELECT * FROM Actors WHERE IDActor=@ActorID)
	BEGIN
		RETURN
	END

	INSERT INTO ActorsMoviesRelationship(ActorID, MovieID) VALUES (@ActorID, @MovieID)

	SET @Success = 1
END
GO

CREATE OR ALTER PROC removeActorFromMovie
(
	@ActorID INT,
	@MovieID INT,
	@Success INT OUT
)
AS
BEGIN
	SET @Success = 0

	IF NOT EXISTS(SELECT * FROM ActorsMoviesRelationship WHERE ActorID=@ActorID AND MovieID=@MovieID)
	BEGIN
		SET @Success = 1 -- it's as if it succeeded
		RETURN
	END

	DELETE FROM ActorsMoviesRelationship WHERE MovieID=@MovieID AND ActorID=@ActorID
	SET @Success = 1
END
GO

CREATE TABLE DirectorsMoviesRelationship (
	DirectorID INT,
	MovieID INT,
	FOREIGN KEY (DirectorID) REFERENCES Directors(IDDirector),
	FOREIGN KEY (MovieID) REFERENCES Movies(IDMovie)
)
GO

CREATE OR ALTER PROC selectDirectorsForMovie
(
	@MovieID INT
)
AS
BEGIN
	SELECT * FROM Directors WHERE IDDirector 
		IN (SELECT DirectorID FROM DirectorsMoviesRelationship WHERE MovieID=@MovieID)
END
GO

CREATE OR ALTER PROC addDirectorToMovie
(
	@DirectorID INT,
	@MovieID INT,
	@Success INT OUT
)
AS 
BEGIN
	SET @Success = 0

	IF EXISTS(SELECT * FROM DirectorsMoviesRelationship WHERE DirectorID=@DirectorID AND MovieID=@MovieID)
	BEGIN
		SET @Success = 1 -- it's as if it succeeded
		RETURN
	END
	
	IF NOT EXISTS(SELECT * FROM Movies WHERE IDMovie=@MovieID)
	BEGIN
		RETURN
	END

	IF NOT EXISTS(SELECT * FROM Directors WHERE IDDirector=@DirectorID)
	BEGIN
		RETURN
	END

	INSERT INTO DirectorsMoviesRelationship(DirectorID, MovieID) VALUES (@DirectorID, @MovieID)

	SET @Success = 1
END
GO

CREATE OR ALTER PROC removeDirectorFromMovie
(
	@DirectorID INT,
	@MovieID INT,
	@Success INT OUT
)
AS
BEGIN
	SET @Success = 0

	IF NOT EXISTS(SELECT * FROM DirectorsMoviesRelationship WHERE DirectorID=@DirectorID AND MovieID=@MovieID)
	BEGIN
		SET @Success = 1 -- it's as if it succeeded
		RETURN
	END

	DELETE FROM DirectorsMoviesRelationship WHERE MovieID=@MovieID AND DirectorID=@DirectorID
	SET @Success = 1
END
GO

-------

CREATE OR ALTER PROCEDURE clearDatabase
AS
BEGIN
	DELETE FROM DirectorsMoviesRelationship;
	DELETE FROM ActorsMoviesRelationship;
	DELETE FROM Actors;
	DELETE FROM Directors;
	DELETE FROM Movies;
END
GO


------------------------------------------------------------
-- create user --
EXEC createUser 'admin', 'admin', 'ADMIN', NULL, NULL;
EXEC createUser 'simone', 'password', 'USER', NULL, NULL; -- my friend simone
------------------------------------------------------------
