CREATE TABLE IF NOT EXISTS movie (
   id BIGSERIAL PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   launch_date DATE NOT NULL,
   rank DECIMAL(3,1) NOT NULL CHECK (rank >= 0 AND rank <= 10),
   revenue DECIMAL(15,2) NOT NULL,
   created_date TIMESTAMP NOT NULL,
   updated_date TIMESTAMP NOT NULL
);