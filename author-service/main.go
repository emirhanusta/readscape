package main

import (
	"author-service/common/app"
	"author-service/common/postgresql"
	"author-service/controller"
	"author-service/persistence"
	"author-service/service"
	"context"
	"github.com/labstack/echo/v4"
)

func main() {
	ctx := context.Background()
	e := echo.New()

	configurationManager := app.NewConfigurationManager()

	dbPool := postgresql.GetConnectionPool(ctx, configurationManager.PostgreSqlConfig)

	authorRepository := persistence.NewAuthorRepository(dbPool)

	authorService := service.NewAuthorService(authorRepository)

	authorController := controller.NewAuthorController(authorService)

	authorController.RegisterRoutes(e)

	err := e.Start(":8081")
	if err != nil {
		return
	}
}
