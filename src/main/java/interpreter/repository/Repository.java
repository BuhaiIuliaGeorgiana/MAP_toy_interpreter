package interpreter.repository;

import interpreter.PrgState;

import java.util.List;

public class Repository implements IRepository{
    private List<PrgState> prg;

    public Repository(PrgState state){

    }
    @Override
    public PrgState getCrtPrg() {
        return this.prg.getLast();
    }
}
