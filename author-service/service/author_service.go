package service

import (
	"author-service/domain"
	"author-service/dto/request"
	"author-service/dto/response"
	"author-service/persistence"
	"github.com/gofrs/uuid/v5"
	"time"
)

type IAuthorService interface {
	Add(dto request.AuthorRequest) (response.AuthorResponse, error)
	GetAll() []response.AuthorResponse
	GetById(id uuid.UUID) (domain.Author, error)
	Update(id uuid.UUID, dto request.AuthorRequest) (response.AuthorResponse, error)
	DeleteById(id uuid.UUID) error
}

type AuthorService struct {
	authorRepository persistence.IAuthorRepository
}

func NewAuthorService(authorRepository persistence.IAuthorRepository) IAuthorService {
	return &AuthorService{authorRepository: authorRepository}
}

func (authorService *AuthorService) Add(request request.AuthorRequest) (response.AuthorResponse, error) {
	author := domain.Author{
		ID:        uuid.Must(uuid.NewV4()),
		Name:      request.Name,
		Biography: request.Biography,
		BirthDate: request.BirthDate,
		CreatedAt: time.Now(),
		UpdatedAt: time.Now(),
	}
	err := authorService.authorRepository.AddAuthor(author)
	if err != nil {
		return response.AuthorResponse{}, err
	}
	return response.ToResponse(author), nil
}

func (authorService *AuthorService) GetAll() []response.AuthorResponse {
	allAuthors := authorService.authorRepository.GetAllAuthors()
	return response.ToResponseList(allAuthors)
}

func (authorService *AuthorService) GetById(id uuid.UUID) (domain.Author, error) {
	return authorService.authorRepository.GetById(id)
}

func (authorService *AuthorService) Update(id uuid.UUID, request request.AuthorRequest) (response.AuthorResponse, error) {
	updateResponse, err := authorService.authorRepository.UpdateAuthor(id, request)
	if err != nil {
		return response.AuthorResponse{}, err
	}
	return updateResponse, nil
}

func (authorService *AuthorService) DeleteById(id uuid.UUID) error {
	return authorService.authorRepository.DeleteById(id)
}
