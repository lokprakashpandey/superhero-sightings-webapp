USE heroDb;
-- Superpower
INSERT INTO superpower (name, description) VALUES 
 ('Webbing', 'The ability to shoot webs whenever in need');
INSERT INTO superpower (name, description) VALUES 
 ('Superstrength', 'The unhuman ability to become very powerful and strong');
INSERT INTO superpower (name, description) VALUES 
 ('Invisibility', 'The power to become invisible at will');
INSERT INTO superpower (name, description) VALUES 
 ('Flight', 'The ability to fly through the air without any equipment');
INSERT INTO superpower (name, description) VALUES 
 ('Superintellect', 'The intellectual ability to know everything');
-- Location
INSERT INTO location (name, description, address, latitude, longitude) VALUES 
 ('Ridgeview Park', 'A big park in New York downtown', 'New York, NY 10002', 40.7829, -73.9654);
INSERT INTO location (name, description, address, latitude, longitude) VALUES 
 ('Statue of Liberty', 'A national monument in New York Harbor', 'Liberty Island, New York, NY 10004', 40.6892, -74.0445);
INSERT INTO location (name, description, address, latitude, longitude) VALUES 
 ('Golden Gate Bridge', 'A suspension bridge connecting San Francisco and Marin County', 'San Francisco, CA 94129', 37.8199, -122.4783); 
INSERT INTO location (name, description, address, latitude, longitude) VALUES 
 ('Grand Canyon National Park', 'A national park in Arizona known for its breathtaking views', 'Grand Canyon Village, AZ 86023', 36.1069, -112.1121); 
INSERT INTO location (name, description, address, latitude, longitude) VALUES 
 ('Central Park', 'A large public park in the heart of Manhattan', 'New York, NY 10022', 40.7821, -73.9654); 
-- Organization
INSERT INTO organization (name, description, address, contactNumber) VALUES 
 ('Avengers', 'An entertainment organization dealing with fictional superheroes', 'New York City, New York, United States, 10001', '2125764000'); 
INSERT INTO organization (name, description, address, contactNumber) VALUES 
 ('Warner Bros', 'An entertainment conglomerate', 'New York City, New York, United States, 10002', '8189546777');
INSERT INTO organization (name, description, address, contactNumber) VALUES 
 ('DC Comics', 'A comic book publisher and entertainment company', 'Burbank, California, United States, 91522', '8187775300');
INSERT INTO organization (name, description, address, contactNumber) VALUES 
 ('Disney', 'An amusement park company', 'Anaheim, California, United States, 92802', '7145205060'); 
INSERT INTO organization (name, description, address, contactNumber) VALUES 
 ('Netflix', 'A streaming service company', 'Los Gatos, California, United States, 95032', '4085403700'); 
-- Superhero
INSERT INTO superhero (name, description, image, superpowerId) VALUES
 ('Spiderman', 'An ordinary young man in his 20s living in the city', LOAD_FILE('/Users/lokpandey/Desktop/superheroes/spiderman.jpeg'), 1);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (1,1);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (1,4);
INSERT INTO superhero (name, description, image, superpowerId) VALUES
 ('Hulk', 'A doctor who got exposed to radiation and became greenish and huge', LOAD_FILE('/Users/lokpandey/Desktop/superheroes/hulk.jpeg'), 2); 
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (2,1);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (2,4); 
INSERT INTO superhero (name, description, image, superpowerId) VALUES
 ('Superman', 'A human with superhuman features', LOAD_FILE('/Users/lokpandey/Desktop/superheroes/superman.jpeg'), 2);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (3,2);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (3,3); 
INSERT INTO superhero (name, description, image, superpowerId) VALUES
 ('Batman', 'Highly trained superhero', LOAD_FILE('/Users/lokpandey/Desktop/superheroes/batman.jpeg'), 5);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (4,2); 
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (4,3); 
INSERT INTO superhero (name, description, image, superpowerId) VALUES
 ('Jessica Jones', 'A super lady', LOAD_FILE('/Users/lokpandey/Desktop/superheroes/jessicajones.jpeg'), 3);
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (5,4);  
INSERT INTO  superhero_organization (superheroId, organizationId) VALUES (5,5); 
-- Sighting
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2010-01-10', 1, 1);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2011-01-10', 1, 2);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2012-01-10', 2, 3);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2013-01-10', 2, 4);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2014-01-10', 2, 5);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2015-01-10', 3, 1);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2016-01-10', 3, 2);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2017-01-10', 3, 4);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2018-01-10', 3, 5);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2019-01-10', 4, 2);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2020-01-10', 4, 3);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2021-01-10', 4, 4);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2022-01-10', 4, 5);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2023-01-10', 5, 3);
INSERT INTO sighting (date, superheroId, locationId) VALUES ('2023-02-10', 5, 5);
