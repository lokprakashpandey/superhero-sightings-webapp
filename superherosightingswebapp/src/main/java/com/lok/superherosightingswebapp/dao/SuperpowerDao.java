/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-15
 * purpose: Dao for Superpower entity
 */
package com.lok.superherosightingswebapp.dao;

import com.lok.superherosightingswebapp.dto.Superpower;
import java.util.List;

/**
 *
 * @author root
 */
public interface SuperpowerDao {

    Superpower getSuperpowerById(int id);

    List<Superpower> getAllSuperpowers();

    Superpower addSuperpower(Superpower superpower);

    void updateSuperpower(Superpower superpower);

    void deleteSuperpowerById(int id);

}
