package request

import (
	"github.com/jackc/pgtype"
)

type AuthorRequest struct {
	Name      string      `json:"name"`
	Biography string      `json:"biography"`
	BirthDate pgtype.Date `json:"birthDate"`
}
