/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Adding service layer for Superpower
 */
package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dto.Superpower;
import java.util.List;

/**
 *
 * @author root
 */
public interface SuperpowerService {
    
    Superpower getSuperpowerById(int id);

    List<Superpower> getAllSuperpowers();

    Superpower addSuperpower(Superpower superpower);

    void updateSuperpower(Superpower superpower);

    void deleteSuperpowerById(int id);
}
