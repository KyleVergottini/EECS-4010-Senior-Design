USE [master]
GO
/****** Object:  Database [ConventionMappingApplication]    Script Date: 9/8/2016 5:25:31 PM ******/
CREATE DATABASE [ConventionMappingApplication]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'ConventionMappingApplication', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\ConventionMappingApplication.mdf' , SIZE = 3072KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'ConventionMappingApplication_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL12.SQLEXPRESS\MSSQL\DATA\ConventionMappingApplication_log.ldf' , SIZE = 1024KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [ConventionMappingApplication] SET COMPATIBILITY_LEVEL = 120
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [ConventionMappingApplication].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [ConventionMappingApplication] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET ARITHABORT OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [ConventionMappingApplication] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [ConventionMappingApplication] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET  DISABLE_BROKER 
GO
ALTER DATABASE [ConventionMappingApplication] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [ConventionMappingApplication] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [ConventionMappingApplication] SET  MULTI_USER 
GO
ALTER DATABASE [ConventionMappingApplication] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [ConventionMappingApplication] SET DB_CHAINING OFF 
GO
ALTER DATABASE [ConventionMappingApplication] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [ConventionMappingApplication] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
ALTER DATABASE [ConventionMappingApplication] SET DELAYED_DURABILITY = DISABLED 
GO
USE [ConventionMappingApplication]
GO
/****** Object:  Table [dbo].[ConventionRole]    Script Date: 9/8/2016 5:25:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[ConventionRole](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[ConventionID] [int] NOT NULL,
	[Role] [nvarchar](25) NOT NULL,
 CONSTRAINT [PK_ConventionRole] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Conventions]    Script Date: 9/8/2016 5:25:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Conventions](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](256) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[Address] [nvarchar](100) NOT NULL,
	[City] [nvarchar] (60) NOT NULL,
	[State] [nvarchar](20) NOT NULL,
	[ZipCode] [int] NOT NULL,
	[Description] [nvarchar](256) NULL,
	[Map1] [image] NULL,
	[Map2] [image] NULL,
	[Map3] [image] NULL,
 CONSTRAINT [PK_Conventions] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Events]    Script Date: 9/8/2016 5:25:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Events](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[RoomID] [int] NOT NULL,
	[Name] [nvarchar](100) NOT NULL,
	[StartDate] [datetime] NOT NULL,
	[EndDate] [datetime] NOT NULL,
	[Description] [nvarchar](256) NULL,
 CONSTRAINT [PK_Events] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Rooms]    Script Date: 9/8/2016 5:25:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Rooms](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[ConventionID] [int] NOT NULL,
	[Name] [nvarchar](256) NOT NULL,
	[Level] [int] NOT NULL,
	[XCoordinate] [int] NOT NULL,
	[YCoordinate] [int] NOT NULL,
 CONSTRAINT [PK_Rooms] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Schedules]    Script Date: 9/8/2016 5:25:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Schedules](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[ConventionID] [int] NOT NULL,
	[EventID] [int] NOT NULL,
 CONSTRAINT [PK_Schedules] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Users]    Script Date: 9/8/2016 5:25:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[ID] [int] IDENTITY(1,1) NOT NULL,
	[Username] [nvarchar](25) NOT NULL,
	[HashedPassword] [nvarchar](256) NOT NULL,
	[PasswordSalt] [nvarchar](256) NOT NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[ConventionRole]  WITH CHECK ADD  CONSTRAINT [FK_ConventionRole_ConventionID] FOREIGN KEY([ConventionID])
REFERENCES [dbo].[Conventions] ([ID])
GO
ALTER TABLE [dbo].[ConventionRole] CHECK CONSTRAINT [FK_ConventionRole_ConventionID]
GO
ALTER TABLE [dbo].[ConventionRole]  WITH CHECK ADD  CONSTRAINT [FK_ConventionRole_UserID] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([ID])
GO
ALTER TABLE [dbo].[ConventionRole] CHECK CONSTRAINT [FK_ConventionRole_UserID]
GO
ALTER TABLE [dbo].[Events]  WITH CHECK ADD  CONSTRAINT [FK_Event_RoomID] FOREIGN KEY([RoomID])
REFERENCES [dbo].[Rooms] ([ID])
GO
ALTER TABLE [dbo].[Events] CHECK CONSTRAINT [FK_Event_RoomID]
GO
ALTER TABLE [dbo].[Rooms]  WITH CHECK ADD  CONSTRAINT [FK_Room_ConventionID] FOREIGN KEY([ConventionID])
REFERENCES [dbo].[Conventions] ([ID])
GO
ALTER TABLE [dbo].[Rooms] CHECK CONSTRAINT [FK_Room_ConventionID]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedule_ConventionID] FOREIGN KEY([ConventionID])
REFERENCES [dbo].[Conventions] ([ID])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedule_ConventionID]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedule_EventID] FOREIGN KEY([EventID])
REFERENCES [dbo].[Events] ([ID])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedule_EventID]
GO
ALTER TABLE [dbo].[Schedules]  WITH CHECK ADD  CONSTRAINT [FK_Schedule_UserID] FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([ID])
GO
ALTER TABLE [dbo].[Schedules] CHECK CONSTRAINT [FK_Schedule_UserID]
GO
USE [master]
GO
ALTER DATABASE [ConventionMappingApplication] SET  READ_WRITE 
GO
