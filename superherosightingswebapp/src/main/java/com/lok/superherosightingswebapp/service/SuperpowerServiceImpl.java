/*
 * @author Lok Prakash Pandey
 * email: lokprakashpandey@gmail.com
 * date: 2023-03-28
 * purpose: Service layer implementation for Superpower
 */

package com.lok.superherosightingswebapp.service;

import com.lok.superherosightingswebapp.dao.SuperpowerDao;
import com.lok.superherosightingswebapp.dto.Superpower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SuperpowerServiceImpl implements SuperpowerService {

    @Autowired
    private SuperpowerDao superpowerDao;

    @Override
    public Superpower getSuperpowerById(int id) {
        return superpowerDao.getSuperpowerById(id);
    }
    
    @Override
    public List<Superpower> getAllSuperpowers() {
        return superpowerDao.getAllSuperpowers();
    }
    
    @Transactional
    @Override
    public Superpower addSuperpower(Superpower superpower) {
        return superpowerDao.addSuperpower(superpower);
    }

    @Transactional
    @Override
    public void updateSuperpower(Superpower superpower) {
        superpowerDao.updateSuperpower(superpower);
    }

    @Transactional
    @Override
    public void deleteSuperpowerById(int id) {
        superpowerDao.deleteSuperpowerById(id);
    }

}