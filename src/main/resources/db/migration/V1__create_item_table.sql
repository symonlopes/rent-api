CREATE TABLE "item" (
    "id" UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL,
    "details" TEXT
);