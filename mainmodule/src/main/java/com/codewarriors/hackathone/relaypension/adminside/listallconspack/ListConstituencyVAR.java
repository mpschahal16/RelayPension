package com.codewarriors.hackathone.relaypension.adminside.listallconspack;

/**
 * Created by hp on 21-03-2018.
 */

public class ListConstituencyVAR {
    String constituencyname;
    Long nooformin_ready,nooformin_queue;
    int maxlimit;

    public ListConstituencyVAR(String constituencyname, Long nooformin_ready, Long nooformin_queue, int maxlimit) {
        this.constituencyname = constituencyname;
        this.nooformin_ready = nooformin_ready;
        this.nooformin_queue = nooformin_queue;
        this.maxlimit = maxlimit;
    }

    public String getConstituencyname() {
        return constituencyname;
    }

    public Long getNooformin_ready() {
        return nooformin_ready;
    }

    public Long getNooformin_queue() {
        return nooformin_queue;
    }

    public int getMaxlimit() {
        return maxlimit;
    }
}
