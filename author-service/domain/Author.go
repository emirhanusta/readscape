package domain

import (
	"github.com/gofrs/uuid/v5"
	"github.com/jackc/pgtype"
	"time"
)

type Author struct {
	ID        uuid.UUID   `json:"id"`
	Name      string      `json:"name"`
	Biography string      `json:"biography"`
	BirthDate pgtype.Date `json:"birthDate"`
	CreatedAt time.Time   `json:"createsAt"`
	UpdatedAt time.Time   `json:"updatedAt"`
}
