USE [master]
GO

/****** Object:  Database [Twister]    Script Date: 24/02/2018 19:20:59 ******/
CREATE DATABASE [Twister]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'Twister', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Twister.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'Twister_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\Twister_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO

ALTER DATABASE [Twister] SET COMPATIBILITY_LEVEL = 140
GO

IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Twister].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO

ALTER DATABASE [Twister] SET ANSI_NULL_DEFAULT OFF
GO

ALTER DATABASE [Twister] SET ANSI_NULLS OFF
GO

ALTER DATABASE [Twister] SET ANSI_PADDING OFF
GO

ALTER DATABASE [Twister] SET ANSI_WARNINGS OFF
GO

ALTER DATABASE [Twister] SET ARITHABORT OFF
GO

ALTER DATABASE [Twister] SET AUTO_CLOSE OFF
GO

ALTER DATABASE [Twister] SET AUTO_SHRINK OFF
GO

ALTER DATABASE [Twister] SET AUTO_UPDATE_STATISTICS ON
GO

ALTER DATABASE [Twister] SET CURSOR_CLOSE_ON_COMMIT OFF
GO

ALTER DATABASE [Twister] SET CURSOR_DEFAULT  GLOBAL
GO

ALTER DATABASE [Twister] SET CONCAT_NULL_YIELDS_NULL OFF
GO

ALTER DATABASE [Twister] SET NUMERIC_ROUNDABORT OFF
GO

ALTER DATABASE [Twister] SET QUOTED_IDENTIFIER OFF
GO

ALTER DATABASE [Twister] SET RECURSIVE_TRIGGERS OFF
GO

ALTER DATABASE [Twister] SET  DISABLE_BROKER
GO

ALTER DATABASE [Twister] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO

ALTER DATABASE [Twister] SET DATE_CORRELATION_OPTIMIZATION OFF
GO

ALTER DATABASE [Twister] SET TRUSTWORTHY OFF
GO

ALTER DATABASE [Twister] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO

ALTER DATABASE [Twister] SET PARAMETERIZATION SIMPLE
GO

ALTER DATABASE [Twister] SET READ_COMMITTED_SNAPSHOT OFF
GO

ALTER DATABASE [Twister] SET HONOR_BROKER_PRIORITY OFF
GO

ALTER DATABASE [Twister] SET RECOVERY FULL
GO

ALTER DATABASE [Twister] SET  MULTI_USER
GO

ALTER DATABASE [Twister] SET PAGE_VERIFY CHECKSUM
GO

ALTER DATABASE [Twister] SET DB_CHAINING OFF
GO

ALTER DATABASE [Twister] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO

ALTER DATABASE [Twister] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO

ALTER DATABASE [Twister] SET DELAYED_DURABILITY = DISABLED
GO

ALTER DATABASE [Twister] SET QUERY_STORE = OFF
GO

USE [Twister]
GO

ALTER DATABASE SCOPED CONFIGURATION SET IDENTITY_CACHE = ON;
GO

ALTER DATABASE SCOPED CONFIGURATION SET LEGACY_CARDINALITY_ESTIMATION = OFF;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET LEGACY_CARDINALITY_ESTIMATION = PRIMARY;
GO

ALTER DATABASE SCOPED CONFIGURATION SET MAXDOP = 0;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET MAXDOP = PRIMARY;
GO

ALTER DATABASE SCOPED CONFIGURATION SET PARAMETER_SNIFFING = ON;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET PARAMETER_SNIFFING = PRIMARY;
GO

ALTER DATABASE SCOPED CONFIGURATION SET QUERY_OPTIMIZER_HOTFIXES = OFF;
GO

ALTER DATABASE SCOPED CONFIGURATION FOR SECONDARY SET QUERY_OPTIMIZER_HOTFIXES = PRIMARY;
GO

ALTER DATABASE [Twister] SET  READ_WRITE
GO

USE [Twister]
GO

/****** Object:  Table [dbo].[Users]    Script Date: 24/02/2018 19:21:35 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Users](
	[user_id] [int] IDENTITY(1,1) NOT NULL,
	[nom] [varchar](50) NOT NULL,
	[prenom] [varchar](100) NOT NULL,
	[login] [varchar](100) NOT NULL,
	[password] [varchar](100) NOT NULL,
 CONSTRAINT [PK_Users_1] PRIMARY KEY CLUSTERED
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY],
 CONSTRAINT [Unique_Login] UNIQUE NONCLUSTERED
(
	[login] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

USE [Twister]
GO

/****** Object:  Table [dbo].[Friends]    Script Date: 24/02/2018 19:22:04 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Friends](
  [from_id] [int] NOT NULL,
  [to_id] [int] NOT NULL,
  [timestamp] [timestamp] NOT NULL,
  CONSTRAINT [PK_Friends] PRIMARY KEY CLUSTERED
    (
      [from_id] ASC,
      [to_id] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Friends]  WITH CHECK ADD  CONSTRAINT [FK_Friends_Users] FOREIGN KEY([to_id])
REFERENCES [dbo].[Users] ([user_id])
GO

ALTER TABLE [dbo].[Friends] CHECK CONSTRAINT [FK_Friends_Users]
GO

ALTER TABLE [dbo].[Friends]  WITH CHECK ADD  CONSTRAINT [FK_Friends_Users1] FOREIGN KEY([from_id])
REFERENCES [dbo].[Users] ([user_id])
GO

ALTER TABLE [dbo].[Friends] CHECK CONSTRAINT [FK_Friends_Users1]
GO


USE [Twister]
GO

/****** Object:  Table [dbo].[Connection]    Script Date: 24/02/2018 19:22:19 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Connection](
  [connectionKey] [varchar](32) NOT NULL,
  [id] [int] NOT NULL,
  [root] [bit] NOT NULL,
  [timestamp] [timestamp] NOT NULL,
  CONSTRAINT [PK_Connection] PRIMARY KEY CLUSTERED
    (
      [connectionKey] ASC
    )WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO

ALTER TABLE [dbo].[Connection]  WITH CHECK ADD  CONSTRAINT [FK_Connection_Users] FOREIGN KEY([id])
REFERENCES [dbo].[Users] ([user_id])
GO

ALTER TABLE [dbo].[Connection] CHECK CONSTRAINT [FK_Connection_Users]
GO

