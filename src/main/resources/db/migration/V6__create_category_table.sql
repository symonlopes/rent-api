CREATE TABLE "category" (
    "id" UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL UNIQUE
);

ALTER TABLE "item" ADD COLUMN "category_id" UUID NOT NULL;
ALTER TABLE "item" ADD CONSTRAINT "fk_item_category" FOREIGN KEY ("category_id") REFERENCES "category"("id");