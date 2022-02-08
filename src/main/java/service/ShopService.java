package service;

import customException.RecordDoesNotExist;
import repository.BaseRepository;

import java.util.List;

public abstract class ShopService<E, R extends BaseRepository<E>> {

    private R r;

    public ShopService(R r) {
        this.r = r;
    }

    public int save(E e) {
        return r.save(e);
    }

    public void update(E e) {
        r.update(e);
    }

    public List<E> findAll() {
        return r.findAll();
    }

    public void delete(int id) {
        r.delete(id);
    }

    public E findById(int id) {
        E e = r.findById(id);
        if (e == null)
            throw new RecordDoesNotExist();
        return r.findById(id);
    }
}
