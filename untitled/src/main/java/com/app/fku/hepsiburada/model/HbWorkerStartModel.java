package com.app.fku.hepsiburada.model;

public class HbWorkerStartModel {
    Integer workerThreadCount;
    Integer threadSirasi;
    Long kategoriId;

    public Integer getWorkerThreadCount() {
        return workerThreadCount;
    }

    public void setWorkerThreadCount(Integer workerThreadCount) {
        this.workerThreadCount = workerThreadCount;
    }

    public Integer getThreadSirasi() {
        return threadSirasi;
    }

    public void setThreadSirasi(Integer threadSirasi) {
        this.threadSirasi = threadSirasi;
    }

    public Long getKategoriId() {
        return kategoriId;
    }

    public void setKategoriId(Long kategoriId) {
        this.kategoriId = kategoriId;
    }
}
