package interpreter.repository;

import interpreter.PrgState;

import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> prg;

    public Repository(PrgState state){
        this.prg = new ArrayList<>();
        this.prg.add(state);
    }
    @Override
    public PrgState getCrtPrg() {
        return this.prg.getLast();
    }
}
