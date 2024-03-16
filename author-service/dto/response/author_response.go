package response

import (
	"author-service/domain"
	"github.com/gofrs/uuid/v5"
	"github.com/jackc/pgtype"
)

type AuthorResponse struct {
	Id        uuid.UUID   `json:"id"`
	Name      string      `json:"name"`
	Biography string      `json:"biography"`
	BirthDate pgtype.Date `json:"birthDate"`
}

func ToResponse(author domain.Author) AuthorResponse {
	return AuthorResponse{
		Id:        author.ID,
		Name:      author.Name,
		Biography: author.Biography,
		BirthDate: author.BirthDate,
	}
}

func ToResponseList(authors []domain.Author) []AuthorResponse {
	var authorResponses []AuthorResponse
	for _, author := range authors {
		authorResponses = append(authorResponses, ToResponse(author))
	}
	return authorResponses
}
