package persistence

import (
	"author-service/domain"
	"author-service/dto/request"
	"author-service/dto/response"
	"author-service/persistence/common"
	"context"
	"errors"
	"fmt"
	"github.com/gofrs/uuid/v5"
	"github.com/jackc/pgtype"
	"github.com/jackc/pgx/v4"
	"github.com/jackc/pgx/v4/pgxpool"
	"github.com/labstack/gommon/log"
	"time"
)

type IAuthorRepository interface {
	GetAllAuthors() []domain.Author
	AddAuthor(author domain.Author) error
	GetById(id uuid.UUID) (domain.Author, error)
	UpdateAuthor(id uuid.UUID, request request.AuthorRequest) (response.AuthorResponse, error)
	DeleteById(id uuid.UUID) error
}

type AuthorRepository struct {
	dbPool *pgxpool.Pool
}

func NewAuthorRepository(dbPool *pgxpool.Pool) IAuthorRepository {
	return &AuthorRepository{dbPool: dbPool}
}

func (authorRepository *AuthorRepository) GetById(AuthorId uuid.UUID) (domain.Author, error) {
	ctx := context.Background()

	getByIdQuery := "SELECT * FROM authors WHERE id = $1"

	row := authorRepository.dbPool.QueryRow(ctx, getByIdQuery, AuthorId)

	var id uuid.UUID
	var name string
	var biography string
	var birthDate pgtype.Date
	var createdAt, updatedAt time.Time

	err := row.Scan(&id, &name, &biography, &birthDate, &createdAt, &updatedAt)

	if err != nil && err.Error() == common.NOT_FOUND {
		return domain.Author{}, errors.New(fmt.Sprintf("Author with id: %v not found", AuthorId))
	}
	if err != nil {
		log.Errorf("Error while scanning author: %v", err)
		return domain.Author{}, errors.New(fmt.Sprintf("Error while scanning author with id: %v", id))
	}

	return domain.Author{
		ID:        id,
		Name:      name,
		Biography: biography,
		BirthDate: birthDate,
	}, nil
}

func (authorRepository *AuthorRepository) GetAllAuthors() []domain.Author {
	ctx := context.Background()
	rows, err := authorRepository.dbPool.Query(ctx, "SELECT * FROM authors")

	if err != nil {
		log.Errorf("Error while querying authors: %v", err)
		return nil
	}

	return extractAuthors(rows)
}

func (authorRepository *AuthorRepository) AddAuthor(author domain.Author) error {
	ctx := context.Background()

	insertQuery := "INSERT INTO authors (id, name, biography, birth_date, created_at, updated_at) VALUES ($1, $2, $3, $4, $5, $6)"

	newAuthor, err := authorRepository.dbPool.Exec(ctx, insertQuery, author.ID, author.Name, author.Biography, author.BirthDate, author.CreatedAt, author.UpdatedAt)
	if err != nil {
		log.Errorf("Error while inserting author: %v", err)
		return err
	}
	log.Infof("New author inserted: %v", newAuthor)
	return nil
}

func (authorRepository *AuthorRepository) UpdateAuthor(authorId uuid.UUID, request request.AuthorRequest) (response.AuthorResponse, error) {
	ctx := context.Background()

	_, getByIdError := authorRepository.GetById(authorId)
	if getByIdError != nil {
		return response.AuthorResponse{},
			errors.New(fmt.Sprintf("Author with id: %v not found", authorId))
	}

	updateQuery := "UPDATE authors SET name = $1, biography = $2, birth_date = $3, updated_at = $4 WHERE id = $5"

	_, err := authorRepository.dbPool.Exec(ctx, updateQuery, request.Name, request.Biography, request.BirthDate, time.Now(), authorId)
	if err != nil {
		log.Errorf("Error while updating author: %v", err)
	}
	log.Info("Author updated successfully with id: ", authorId)
	return response.AuthorResponse{
		Id:        authorId,
		Name:      request.Name,
		Biography: request.Biography,
		BirthDate: request.BirthDate,
	}, nil
}

func (authorRepository *AuthorRepository) DeleteById(authorId uuid.UUID) error {
	ctx := context.Background()

	_, getByIdError := authorRepository.GetById(authorId)
	if getByIdError != nil {
		return errors.New(fmt.Sprintf("Author with id: %v not found", authorId))
	}

	deleteQuery := "DELETE FROM authors WHERE id = $1"

	_, err := authorRepository.dbPool.Exec(ctx, deleteQuery, authorId)
	if err != nil {
		log.Errorf("Error while deleting author: %v", err)
	}
	log.Info("Author deleted successfully with id: ", authorId)
	return nil
}

func extractAuthors(rows pgx.Rows) []domain.Author {
	var authors []domain.Author
	var id uuid.UUID
	var name, biography string
	var birthDate pgtype.Date
	var createdAt, updatedAt time.Time

	for rows.Next() {
		err := rows.Scan(&id, &name, &biography, &birthDate, &createdAt, &updatedAt)
		if err != nil {
			log.Errorf("Error while scanning authors: %v", err)
			return nil
		}
		authors = append(authors, domain.Author{
			ID:        id,
			Name:      name,
			Biography: biography,
			BirthDate: birthDate,
			CreatedAt: createdAt,
			UpdatedAt: updatedAt,
		})
	}
	return authors
}
