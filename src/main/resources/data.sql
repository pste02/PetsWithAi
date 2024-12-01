-- Insert households
INSERT INTO household (eircode, number_of_occupants, max_number_of_occupants, owner_occupied) VALUES
('D01A1A1', 3, 5, true),
('D02B2B2', 2, 4, false),
('D03C3C3', 4, 6, true),
('D04D4D4', 1, 3, true),
('D05E5E5', 5, 7, false);

-- Insert pets
INSERT INTO pet (name, animal_type, breed, age, household_eircode) VALUES
('Buddy', 'Dog', 'Golden Retriever', 3, 'D01A1A1'),
('Mittens', 'Cat', 'Siamese', 2, 'D01A1A1'),
('Charlie', 'Dog', 'Beagle', 4, 'D02B2B2'),
('Max', 'Dog', 'Bulldog', 5, 'D03C3C3'),
('Bella', 'Cat', 'Persian', 1, 'D03C3C3'),
('Lucy', 'Dog', 'Poodle', 6, 'D04D4D4'),
('Daisy', 'Cat', 'Maine Coon', 3, 'D05E5E5'),
('Rocky', 'Dog', 'Boxer', 2, 'D05E5E5'),
('Luna', 'Cat', 'Bengal', 4, 'D05E5E5'),
('Milo', 'Dog', 'Labrador', 1, 'D05E5E5');
