CREATE TABLE "tag" (
   "id" UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
   "name" VARCHAR(100) NOT NULL UNIQUE,
   "value" VARCHAR(50) NOT NULL UNIQUE
);
