USE [master]
GO
CREATE DATABASE [recsys2013]
GO
USE [recsys2013]
GO
CREATE TABLE [dbo].[Businesses](
	[BusinessId] [nvarchar](50) NOT NULL,
	[FullAddress] [nvarchar](200) NOT NULL,
	[IsOpen] [bit] NOT NULL,
	[City] [nvarchar](200) NOT NULL,
	[Name] [nvarchar](200) NOT NULL,
	[State] [nvarchar](200) NOT NULL,
	[CheckinsCount] [int] NOT NULL,
	[BusinessIntId] [int] NOT NULL,
 CONSTRAINT [PK_Businesses] PRIMARY KEY CLUSTERED 
(
	[BusinessId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Reviews]    Script Date: 24.5.2016 г. 10:11:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reviews](
	[ReviewId] [nvarchar](50) NOT NULL,
	[UserId] [nvarchar](50) NOT NULL,
	[Stars] [float] NOT NULL,
	[Date] [date] NOT NULL,
	[Text] [nvarchar](500) NOT NULL,
	[BusinessId] [nvarchar](50) NOT NULL,
	[IsTrain] [bit] NOT NULL
) ON [PRIMARY]

GO
/****** Object:  Table [dbo].[Users]    Script Date: 24.5.2016 г. 10:11:10 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserId] [nvarchar](50) NOT NULL,
	[Name] [nvarchar](200) NULL,
	[UserIntId] [int] NOT NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD  CONSTRAINT [FK_Reviews_Businesses] FOREIGN KEY([BusinessId])
REFERENCES [dbo].[Businesses] ([BusinessId])
GO
ALTER TABLE [dbo].[Reviews] CHECK CONSTRAINT [FK_Reviews_Businesses]
GO
ALTER TABLE [dbo].[Reviews]  WITH CHECK ADD  CONSTRAINT [FK_Reviews_Users] FOREIGN KEY([UserId])
REFERENCES [dbo].[Users] ([UserId])
GO
ALTER TABLE [dbo].[Reviews] CHECK CONSTRAINT [FK_Reviews_Users]
GO
USE [master]
GO
ALTER DATABASE [recsys2013] SET  READ_WRITE 
GO
