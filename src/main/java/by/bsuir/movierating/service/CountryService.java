package by.bsuir.movierating.service;

import by.bsuir.movierating.domain.Country;

import java.util.List;

/**
 * Provides a business-logic with the Country entity.
 *
 * @author Kostevich Vladislav
 * @version 1.0
 */
public interface CountryService {
    /**
     * Returns a countries ordered by a position number.
     *
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a countries ordered by a position number
     */
    List<Country> getTopPositionCountries(int amount, String languageId);

    /**
     * Returns a concrete amount of the countries from a concrete position.
     *
     * @param from starting position in the countries list (starting from 0)
     * @param amount a needed amount of countries
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a concrete amount of the countries from a concrete position
     */
    List<Country> getCountries(int from, int amount, String languageId);

    /**
     * Returns a total amount of countries in the data storage.
     *
     * @return a total amount of countries in the data storage
     */
    int getCountriesCount();

    /**
     * Returns a certain country by id.
     *
     * @param id an id of a needed country
     * @param languageId a language id like 'EN', "RU' etc.
     * @return a certain country
     */
    Country getCountryById(int id, String languageId);

    /**
     * Adds a new country to the data storage (in the default language).
     *
     * @param name a name of the country
     * @param position a number of a position of the country
     */
    void addCountry(String name, int position);

    /**
     * Edits an already existing country or adds/edits a localization of a country.
     *
     * If the languageId argument is an id of the default language of the application, then it edits
     * a country. If the language argument is an id of the different language (not default) then it
     * adds/edits a localization of a country (it based on the fact of existence of a localization:
     * if it doesn't exist then it will be added, otherwise a localization will be updated).
     * @param id an id of the needed country
     * @param name a new name of the country
     * @param position a new position number of the country
     * @param languageId a language id like 'EN', "RU' etc.
     */
    void editCountry(int id, String name, int position, String languageId);

    /**
     * Deletes an existing country from the data storage (with all of the localizations).
     *
     * @param id an id of the deleting country
     */
    void deleteCountry(int id);
}
