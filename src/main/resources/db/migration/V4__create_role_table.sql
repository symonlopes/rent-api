CREATE TABLE "user_role" (
    "user_id" UUID NOT NULL,
    "description" VARCHAR(30) NOT NULL,
    CONSTRAINT fk_user_id FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE CASCADE
);