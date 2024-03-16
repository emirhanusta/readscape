package controller

import (
	"author-service/dto/request"
	"author-service/dto/response"
	"author-service/service"
	"github.com/gofrs/uuid/v5"
	"github.com/labstack/echo/v4"
	"net/http"
)

type AuthorController struct {
	authorService service.IAuthorService
}

func NewAuthorController(authorService service.IAuthorService) *AuthorController {
	return &AuthorController{authorService: authorService}
}

func (authorController *AuthorController) RegisterRoutes(e *echo.Echo) {
	e.POST("/api/v1/authors", authorController.Add)
	e.GET("/api/v1/authors", authorController.GetAll)
	e.GET("/api/v1/authors/:id", authorController.GetById)
	e.PUT("/api/v1/authors/:id", authorController.Update)
	e.DELETE("/api/v1/authors/:id", authorController.DeleteById)
}

func (authorController *AuthorController) Add(c echo.Context) error {
	var addAuthorRequest request.AuthorRequest
	bindErr := c.Bind(&addAuthorRequest)
	if bindErr != nil {
		return c.JSON(http.StatusBadRequest, response.ErrorResponse{
			ErrorDescription: bindErr.Error(),
		})
	}
	createResponse, err := authorController.authorService.Add(addAuthorRequest)
	if err != nil {
		return c.JSON(http.StatusUnprocessableEntity, response.ErrorResponse{
			ErrorDescription: err.Error(),
		})
	}
	return c.JSON(http.StatusOK, createResponse)
}

func (authorController *AuthorController) GetAll(c echo.Context) error {
	allAuthors := authorController.authorService.GetAll()

	return c.JSON(http.StatusOK, allAuthors)
}

func (authorController *AuthorController) GetById(c echo.Context) error {
	paramId := c.Param("id")
	authorId, _ := uuid.FromString(paramId)
	author, err := authorController.authorService.GetById(authorId)
	if err != nil {
		return c.JSON(http.StatusNotFound, response.ErrorResponse{
			ErrorDescription: err.Error(),
		})
	}

	return c.JSON(http.StatusOK, response.ToResponse(author))
}

func (authorController *AuthorController) Update(c echo.Context) error {
	var authorToUpdate request.AuthorRequest
	bindErr := c.Bind(&authorToUpdate)

	paramId := c.Param("id")
	authorId, _ := uuid.FromString(paramId)

	if bindErr != nil {
		return c.JSON(http.StatusBadRequest, response.ErrorResponse{
			ErrorDescription: bindErr.Error(),
		})
	}
	updateResponse, err := authorController.authorService.Update(authorId, authorToUpdate)
	if err != nil {
		return c.JSON(http.StatusNotFound, response.ErrorResponse{
			ErrorDescription: err.Error(),
		})
	}
	return c.JSON(http.StatusOK, updateResponse)
}

func (authorController *AuthorController) DeleteById(c echo.Context) error {
	paramId := c.Param("id")
	authorId, _ := uuid.FromString(paramId)
	err := authorController.authorService.DeleteById(authorId)
	if err != nil {
		return c.JSON(http.StatusNotFound, response.ErrorResponse{
			ErrorDescription: err.Error(),
		})
	}
	return c.NoContent(http.StatusOK)
}
