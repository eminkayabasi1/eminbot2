package com.app.fku.hepsiburada.fonksiyon.service;

import com.app.fku.genel.fonksiyon.service.GenelService;
import com.app.fku.genel.fonksiyon.service.LogService;
import com.app.fku.hepsiburada.fonksiyon.impl.HbGenelService;
import com.app.fku.hepsiburada.fonksiyon.impl.HepsiBuradaSepetJSONService;
import com.app.fku.hepsiburada.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.openqa.selenium.json.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class HepsiBuradaJSONServiceImpl implements HepsiBuradaSepetJSONService {

    @Autowired
    HbGenelService hbGenelService;

    @Autowired
    GenelService genelService;

    @Autowired
    LogService logService;

    private static final HashMap<String, HbTokenModel> hbTokenHashMap = new HashMap<>();
    private static final List<HbRequestMetadataModel> hbRequestMetadataModelList = new ArrayList<>();

    @Override
    public void sorgula() throws IOException, InterruptedException {
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI1NTIsImV4cCI6MTczNjY2MTc1MiwiaWF0IjoxNzM2NDAyNTUyLCJVc2VySWQiOiI3Y2YxZDZmNS00NjhjLTRkZGEtOTlkZC0zNjNlZGVmODRlZjkiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjFAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiYWQwN2E3NTMtMTE2Ny00YTY1LTgzMWMtOGE4ZjU1OTdiYTc4IiwicCI6eyJ0IjpbXSwiZSI6IitvMFJGODFnVklTM1kwa0s4aGxFSzJQRXlOSjBmMVBMMElHbkFucEJFME09In19.3IyEK7KCoDOOh-i0ScpWSfMwCY-wGZUxb2aTo-upne0",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI1NTIsImV4cCI6MTczNjY2MTc1MiwiaWF0IjoxNzM2NDAyNTUyLCJVc2VySWQiOiI3Y2YxZDZmNS00NjhjLTRkZGEtOTlkZC0zNjNlZGVmODRlZjkiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjFAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiYWQwN2E3NTMtMTE2Ny00YTY1LTgzMWMtOGE4ZjU1OTdiYTc4IiwicCI6eyJ0IjpbXSwiZSI6IitvMFJGODFnVklTM1kwa0s4aGxFSzJQRXlOSjBmMVBMMElHbkFucEJFME09In19.3IyEK7KCoDOOh-i0ScpWSfMwCY-wGZUxb2aTo-upne0"));//emin.1@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI2MDUsImV4cCI6MTczNjY2MTgwNSwiaWF0IjoxNzM2NDAyNjA1LCJVc2VySWQiOiI2ZGY0ZDA3YS1lZTExLTQ5NDMtYWQyNC05NjYxNjdlODkzYTIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjJAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiMDdjYjU5MjYtZTkyMS00YTZjLTg0ODAtNjczYmJlOGY0MjBiIiwicCI6eyJ0IjpbXSwiZSI6IkVFcXcwVG5hNkxKTVBoRTNxcXVSTVdvNkttUWo1NjdLSVJ3N0JqZjlwRDA9In19.hMb43fhaYXIbPN0hPyCk78nj1841cy87QTiwCb7TvlM",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI2MDUsImV4cCI6MTczNjY2MTgwNSwiaWF0IjoxNzM2NDAyNjA1LCJVc2VySWQiOiI2ZGY0ZDA3YS1lZTExLTQ5NDMtYWQyNC05NjYxNjdlODkzYTIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjJAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiMDdjYjU5MjYtZTkyMS00YTZjLTg0ODAtNjczYmJlOGY0MjBiIiwicCI6eyJ0IjpbXSwiZSI6IkVFcXcwVG5hNkxKTVBoRTNxcXVSTVdvNkttUWo1NjdLSVJ3N0JqZjlwRDA9In19.hMb43fhaYXIbPN0hPyCk78nj1841cy87QTiwCb7TvlM"));//emin.2@atlaspsota.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI2NTMsImV4cCI6MTczNjY2MTg1MywiaWF0IjoxNzM2NDAyNjUzLCJVc2VySWQiOiJkZjQ3Y2UyZS05NjQ3LTRiNTYtOWU5Yi05NGE0ZWQ3ZDdkMmMiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjNAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiM2ZjYWRkNDktOTM4Ni00ZDM1LWIzMWEtYjQwMjg0ZTYwYzk2IiwicCI6eyJ0IjpbXSwiZSI6ImE2S3lNZDdxZmtEVEtRS3UxSkpxaDlJOHk5cEJ1ZDQ2Z1A1VWlZL1RqUWM9In19.hxZYiwwChImX3SCSqqiEbbJGHK9TamLUKBNjRPykIsQ",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI2NTMsImV4cCI6MTczNjY2MTg1MywiaWF0IjoxNzM2NDAyNjUzLCJVc2VySWQiOiJkZjQ3Y2UyZS05NjQ3LTRiNTYtOWU5Yi05NGE0ZWQ3ZDdkMmMiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjNAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiM2ZjYWRkNDktOTM4Ni00ZDM1LWIzMWEtYjQwMjg0ZTYwYzk2IiwicCI6eyJ0IjpbXSwiZSI6ImE2S3lNZDdxZmtEVEtRS3UxSkpxaDlJOHk5cEJ1ZDQ2Z1A1VWlZL1RqUWM9In19.hxZYiwwChImX3SCSqqiEbbJGHK9TamLUKBNjRPykIsQ"));//emin.3@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI3MDMsImV4cCI6MTczNjY2MTkwMywiaWF0IjoxNzM2NDAyNzAzLCJVc2VySWQiOiJhNjE2OGNlYy00ZmI0LTRlYmQtOTk2NS04ZWEyZDcyMDVjN2EiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjRAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZTRhYzdlZjktM2VlOC00YmM2LTk2MmYtNjFmMDQxMTYwN2VmIiwicCI6eyJ0IjpbXSwiZSI6IkY2aTFvVmNZZzk5RWZZR09kTk4xRlo2UWhjd2t6UHRPNnJxQUxWb1dJVGs9In19.tVpBASk1khJD-pNG5fu4Hj6vqa1XVlW-57OOCuxe9mk",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI3MDMsImV4cCI6MTczNjY2MTkwMywiaWF0IjoxNzM2NDAyNzAzLCJVc2VySWQiOiJhNjE2OGNlYy00ZmI0LTRlYmQtOTk2NS04ZWEyZDcyMDVjN2EiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjRAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZTRhYzdlZjktM2VlOC00YmM2LTk2MmYtNjFmMDQxMTYwN2VmIiwicCI6eyJ0IjpbXSwiZSI6IkY2aTFvVmNZZzk5RWZZR09kTk4xRlo2UWhjd2t6UHRPNnJxQUxWb1dJVGs9In19.tVpBASk1khJD-pNG5fu4Hj6vqa1XVlW-57OOCuxe9mk"));//emin.4@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI3NjQsImV4cCI6MTczNjY2MTk2NCwiaWF0IjoxNzM2NDAyNzY0LCJVc2VySWQiOiJiMWNkMWE0Mi1lMzBiLTQ5NzUtYmVlOC0yOTg5NDIxMjJjNmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjVAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZDc3M2YyYmMtNDAxMi00NmU0LThhNzItMDZkODRjZTk0MzA4IiwicCI6eyJ0IjpbXSwiZSI6ImtFWjhsVnJFdEthZ20xZ1RvaFI0QXhGSFBDdWU4TkVaRXN5ckxQdmJjWXM9In19.tDypfzvqI6IuM6z7mjAv9h6VzMFnsKeoCqeagPkeuDw",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI3NjQsImV4cCI6MTczNjY2MTk2NCwiaWF0IjoxNzM2NDAyNzY0LCJVc2VySWQiOiJiMWNkMWE0Mi1lMzBiLTQ5NzUtYmVlOC0yOTg5NDIxMjJjNmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjVAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZDc3M2YyYmMtNDAxMi00NmU0LThhNzItMDZkODRjZTk0MzA4IiwicCI6eyJ0IjpbXSwiZSI6ImtFWjhsVnJFdEthZ20xZ1RvaFI0QXhGSFBDdWU4TkVaRXN5ckxQdmJjWXM9In19.tDypfzvqI6IuM6z7mjAv9h6VzMFnsKeoCqeagPkeuDw"));//emin.5@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI4MTksImV4cCI6MTczNjY2MjAxOSwiaWF0IjoxNzM2NDAyODE5LCJVc2VySWQiOiJkMDQwNWJlYy01YjFkLTQ0Y2MtOWI5OS1kYTJiOTRlNjJmNzEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjZAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiOTM0YjQ5MjYtNWNlMy00MmUxLTkzMzAtZGNjYzY0NzNjNzM1IiwicCI6eyJ0IjpbXSwiZSI6InlKSkVKNTRHQ2N4ZGtnalorMytxNS8wdGU5bi8xU2VXS0wzN0tDK3MwdE09In19.BmWcUHniRdLCFzjo2CFqrtEM4ERPfa3MW_CPoBSbHq8",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI4MTksImV4cCI6MTczNjY2MjAxOSwiaWF0IjoxNzM2NDAyODE5LCJVc2VySWQiOiJkMDQwNWJlYy01YjFkLTQ0Y2MtOWI5OS1kYTJiOTRlNjJmNzEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjZAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiOTM0YjQ5MjYtNWNlMy00MmUxLTkzMzAtZGNjYzY0NzNjNzM1IiwicCI6eyJ0IjpbXSwiZSI6InlKSkVKNTRHQ2N4ZGtnalorMytxNS8wdGU5bi8xU2VXS0wzN0tDK3MwdE09In19.BmWcUHniRdLCFzjo2CFqrtEM4ERPfa3MW_CPoBSbHq8"));//emin.6@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI4NjcsImV4cCI6MTczNjY2MjA2NywiaWF0IjoxNzM2NDAyODY3LCJVc2VySWQiOiIyYTZhMTQ0OS05NTM3LTRkZGUtOGIwOC03ZDFiMWZiYzczNTAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjdAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiNDExOWNjYTQtNjNiMi00YzI3LTg2NGUtZmEyYzlmODY5ZmYxIiwicCI6eyJ0IjpbXSwiZSI6InVXeUFGRG5oOFNUWFpldmdQTDJQQi9VWDg3NzRMaXNCdldSNWN5WExDU0E9In19.Gnkq6D8_jeMJ9lSqTKF8rUrHJw6zx3e7FfDSBVXbTBI",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI4NjcsImV4cCI6MTczNjY2MjA2NywiaWF0IjoxNzM2NDAyODY3LCJVc2VySWQiOiIyYTZhMTQ0OS05NTM3LTRkZGUtOGIwOC03ZDFiMWZiYzczNTAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjdAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiNDExOWNjYTQtNjNiMi00YzI3LTg2NGUtZmEyYzlmODY5ZmYxIiwicCI6eyJ0IjpbXSwiZSI6InVXeUFGRG5oOFNUWFpldmdQTDJQQi9VWDg3NzRMaXNCdldSNWN5WExDU0E9In19.Gnkq6D8_jeMJ9lSqTKF8rUrHJw6zx3e7FfDSBVXbTBI"));//emin.7@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI5MTYsImV4cCI6MTczNjY2MjExNiwiaWF0IjoxNzM2NDAyOTE2LCJVc2VySWQiOiIzNmQ3YTU3Mi00YjQ4LTQzMDUtOWE1YS03NWVlNDgxMzRmODciLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjhAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiOTc1MzY2OWItOTJkZi00ZWNjLWI4OWYtYWExMTFmMDBiNzAyIiwicCI6eyJ0IjpbXSwiZSI6IldKZldLR1J3amxQYWxDT0p1cmdHbmt3eGl0NEw2aTU4RHZlTGNqTzc3bnM9In19.vKXqnizQwJuOu_tdZ03HByBzUWkVHmdS_2-RNRAUmDo",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI5MTYsImV4cCI6MTczNjY2MjExNiwiaWF0IjoxNzM2NDAyOTE2LCJVc2VySWQiOiIzNmQ3YTU3Mi00YjQ4LTQzMDUtOWE1YS03NWVlNDgxMzRmODciLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjhAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiOTc1MzY2OWItOTJkZi00ZWNjLWI4OWYtYWExMTFmMDBiNzAyIiwicCI6eyJ0IjpbXSwiZSI6IldKZldLR1J3amxQYWxDT0p1cmdHbmt3eGl0NEw2aTU4RHZlTGNqTzc3bnM9In19.vKXqnizQwJuOu_tdZ03HByBzUWkVHmdS_2-RNRAUmDo"));//emin.8@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI5NTcsImV4cCI6MTczNjY2MjE1NywiaWF0IjoxNzM2NDAyOTU3LCJVc2VySWQiOiJhOGJkNjczYS04MDYzLTQ3MzItYmYxZi1kMGNlYzQ0YzMzZTgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjlAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZWIyYjU2ZmEtZjUwYS00MWZjLTlmMzAtYmJkODU1ODJkMGYzIiwicCI6eyJ0IjpbXSwiZSI6IlArb3hJSWF1WG5VZDFtZ3pTaDB2OFk0VEgyN2FLRWR1enpKR043TitFTGs9In19.F9GVG1zCLW5eR4U8cg7BiALnY-QL8o2D96rlwk3y9rg",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDI5NTcsImV4cCI6MTczNjY2MjE1NywiaWF0IjoxNzM2NDAyOTU3LCJVc2VySWQiOiJhOGJkNjczYS04MDYzLTQ3MzItYmYxZi1kMGNlYzQ0YzMzZTgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjlAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZWIyYjU2ZmEtZjUwYS00MWZjLTlmMzAtYmJkODU1ODJkMGYzIiwicCI6eyJ0IjpbXSwiZSI6IlArb3hJSWF1WG5VZDFtZ3pTaDB2OFk0VEgyN2FLRWR1enpKR043TitFTGs9In19.F9GVG1zCLW5eR4U8cg7BiALnY-QL8o2D96rlwk3y9rg"));//emin.9@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMwMDMsImV4cCI6MTczNjY2MjIwMywiaWF0IjoxNzM2NDAzMDAzLCJVc2VySWQiOiI4OGFlYmViMi01MGIwLTRkNzUtYjZkMi04NzQ2ZTA1MmNkZWEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEwQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6ImYxM2YyZGY2LTk3ZWYtNGZiOS1iZTI4LWM0YWQ3MzdhYjM0MCIsInAiOnsidCI6W10sImUiOiJZSVUwNy9oZjJ4NlU5U3RTMUtrY2JJN3VyTWtYNCtMSTkvSVZZWGo4dkhRPSJ9fQ.KbNjJBj8FQfmv3iv8mytjSg-X3IDsfFU9V6rvvGipw4",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMwMDMsImV4cCI6MTczNjY2MjIwMywiaWF0IjoxNzM2NDAzMDAzLCJVc2VySWQiOiI4OGFlYmViMi01MGIwLTRkNzUtYjZkMi04NzQ2ZTA1MmNkZWEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEwQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6ImYxM2YyZGY2LTk3ZWYtNGZiOS1iZTI4LWM0YWQ3MzdhYjM0MCIsInAiOnsidCI6W10sImUiOiJZSVUwNy9oZjJ4NlU5U3RTMUtrY2JJN3VyTWtYNCtMSTkvSVZZWGo4dkhRPSJ9fQ.KbNjJBj8FQfmv3iv8mytjSg-X3IDsfFU9V6rvvGipw4"));//emin.10@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMwNDQsImV4cCI6MTczNjY2MjI0NCwiaWF0IjoxNzM2NDAzMDQ0LCJVc2VySWQiOiJlMGJlYTljYi05OWRiLTQ3NjktYWQzOC00ZmYyYmJiNTA1MmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjExQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6ImE4OGVhZmZjLWU4MTYtNDU0ZS05NzlmLTUxNWVjYzU0YmVmNCIsInAiOnsidCI6W10sImUiOiJ0UTJ6OFJMS01neHRtSkhsVjdzY2kzZHZZd1JKQVlscGZIT1ZwK0xxWVo0PSJ9fQ.nksIoosG715wLMsEElGJCaEkZsslAjSj3wjSGinu7Fk",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMwNDQsImV4cCI6MTczNjY2MjI0NCwiaWF0IjoxNzM2NDAzMDQ0LCJVc2VySWQiOiJlMGJlYTljYi05OWRiLTQ3NjktYWQzOC00ZmYyYmJiNTA1MmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjExQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6ImE4OGVhZmZjLWU4MTYtNDU0ZS05NzlmLTUxNWVjYzU0YmVmNCIsInAiOnsidCI6W10sImUiOiJ0UTJ6OFJMS01neHRtSkhsVjdzY2kzZHZZd1JKQVlscGZIT1ZwK0xxWVo0PSJ9fQ.nksIoosG715wLMsEElGJCaEkZsslAjSj3wjSGinu7Fk"));//emin.11@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMxMTUsImV4cCI6MTczNjY2MjMxNSwiaWF0IjoxNzM2NDAzMTE1LCJVc2VySWQiOiJhZjZlYmU0ZC1hZjQyLTQ5YTAtYWZjZS1hYWUwMTZmMWI4YjAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEyQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6IjJjZjA5OWEwLWNkNzUtNDViNC05NWZhLTJjNWE1YjI5NTFkYiIsInAiOnsidCI6W10sImUiOiJxNVNXMVROa250R0ZGa0Q2d083djZXT2R1OGdlSEpoenhlZ2ZYUWpwUEg0PSJ9fQ.nBOLgrbRhs6KttO9N466Eem1SI2twY4sihdhF6S1vfo",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMxMTUsImV4cCI6MTczNjY2MjMxNSwiaWF0IjoxNzM2NDAzMTE1LCJVc2VySWQiOiJhZjZlYmU0ZC1hZjQyLTQ5YTAtYWZjZS1hYWUwMTZmMWI4YjAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEyQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6IjJjZjA5OWEwLWNkNzUtNDViNC05NWZhLTJjNWE1YjI5NTFkYiIsInAiOnsidCI6W10sImUiOiJxNVNXMVROa250R0ZGa0Q2d083djZXT2R1OGdlSEpoenhlZ2ZYUWpwUEg0PSJ9fQ.nBOLgrbRhs6KttO9N466Eem1SI2twY4sihdhF6S1vfo"));//emin.12@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMxNjIsImV4cCI6MTczNjY2MjM2MiwiaWF0IjoxNzM2NDAzMTYyLCJVc2VySWQiOiIzODY2MTI1Zi0zMWIyLTQ1MzgtOThkZS01NDlhYmYxODUyNDgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEzQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6IjUxYTJjMzVmLTdhYjktNDc0Mi05NmQ2LTRmZGQwOTlkNzk0ZCIsInAiOnsidCI6W10sImUiOiJmT3p5bjhyc2VCS2pvekVSZmllMGMwSzdycjd1SUx1ZHk5MWIzYTRDd1k4PSJ9fQ.RRB8GaUcirMZl0C3v_AfzWkofmfdGzklnxCyfp_xDuc",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMxNjIsImV4cCI6MTczNjY2MjM2MiwiaWF0IjoxNzM2NDAzMTYyLCJVc2VySWQiOiIzODY2MTI1Zi0zMWIyLTQ1MzgtOThkZS01NDlhYmYxODUyNDgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEzQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6IjUxYTJjMzVmLTdhYjktNDc0Mi05NmQ2LTRmZGQwOTlkNzk0ZCIsInAiOnsidCI6W10sImUiOiJmT3p5bjhyc2VCS2pvekVSZmllMGMwSzdycjd1SUx1ZHk5MWIzYTRDd1k4PSJ9fQ.RRB8GaUcirMZl0C3v_AfzWkofmfdGzklnxCyfp_xDuc"));//emin.13@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMyMDMsImV4cCI6MTczNjY2MjQwMywiaWF0IjoxNzM2NDAzMjAzLCJVc2VySWQiOiJmM2M4NzY2Zi04NjgzLTQ0MmMtOGZiYi1mODc1YTZmOWRkNzIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE0QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6ImE5MGU1OWYzLTQ2NzEtNGE1OC05MGNiLTc5ZWJmN2ZhZmU5NCIsInAiOnsidCI6W10sImUiOiI5VzBQZGVTY2E1SjlMME9rd0piZU5jUHM5THZFKzA4THFIS0s0Q1JTSzRBPSJ9fQ.zfB6hR5CTtElqHYg5OMU8AC7Lkt3VzjN7z-D62r5nco",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMyMDMsImV4cCI6MTczNjY2MjQwMywiaWF0IjoxNzM2NDAzMjAzLCJVc2VySWQiOiJmM2M4NzY2Zi04NjgzLTQ0MmMtOGZiYi1mODc1YTZmOWRkNzIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE0QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6ImE5MGU1OWYzLTQ2NzEtNGE1OC05MGNiLTc5ZWJmN2ZhZmU5NCIsInAiOnsidCI6W10sImUiOiI5VzBQZGVTY2E1SjlMME9rd0piZU5jUHM5THZFKzA4THFIS0s0Q1JTSzRBPSJ9fQ.zfB6hR5CTtElqHYg5OMU8AC7Lkt3VzjN7z-D62r5nco"));//emin.14@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMyNDIsImV4cCI6MTczNjY2MjQ0MiwiaWF0IjoxNzM2NDAzMjQyLCJVc2VySWQiOiIwNjVlYTA3ZS0wNTBmLTRlOTgtOGQ2NS05OGQ0ZjYyMWRjY2IiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE1QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6IjNiM2I1NTI5LTg2NTQtNDMyMi05YjBmLTJiYmRkY2VkOWJkZSIsInAiOnsidCI6W10sImUiOiIxY3o1NzZ6SWMrUmJTL0htMFlSZDBzVnR0cHNUTE5WTnNTbEhaNWdWTXZnPSJ9fQ.xVCTivUQ6TnlSEfU2Uvb4AKJJcXQFjRiDoJk4eWF2uQ",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMyNDIsImV4cCI6MTczNjY2MjQ0MiwiaWF0IjoxNzM2NDAzMjQyLCJVc2VySWQiOiIwNjVlYTA3ZS0wNTBmLTRlOTgtOGQ2NS05OGQ0ZjYyMWRjY2IiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE1QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIlRlbmFudCI6IlNQQSIsIkp0aSI6IjNiM2I1NTI5LTg2NTQtNDMyMi05YjBmLTJiYmRkY2VkOWJkZSIsInAiOnsidCI6W10sImUiOiIxY3o1NzZ6SWMrUmJTL0htMFlSZDBzVnR0cHNUTE5WTnNTbEhaNWdWTXZnPSJ9fQ.xVCTivUQ6TnlSEfU2Uvb4AKJJcXQFjRiDoJk4eWF2uQ"));//emin.15@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMzMTEsImV4cCI6MTczNjY2MjUxMSwiaWF0IjoxNzM2NDAzMzExLCJVc2VySWQiOiJhMzM0YzFlZC0yOTMzLTRlMWItYWVhZC01NmU3MTc0MzI4NzYiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE2QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6Ijg2MDJjM2NmLTBlYzQtNDZjZi04ZDQwLTAyZTRmZDVkNThiMCIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.17KMBYBshZXWilE_3z6PufQvwEyb62-AqrSn_8dIZF4",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMzMTEsImV4cCI6MTczNjY2MjUxMSwiaWF0IjoxNzM2NDAzMzExLCJVc2VySWQiOiJhMzM0YzFlZC0yOTMzLTRlMWItYWVhZC01NmU3MTc0MzI4NzYiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE2QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6Ijg2MDJjM2NmLTBlYzQtNDZjZi04ZDQwLTAyZTRmZDVkNThiMCIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.17KMBYBshZXWilE_3z6PufQvwEyb62-AqrSn_8dIZF4"));//emin.16@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMzOTcsImV4cCI6MTczNjY2MjU5NywiaWF0IjoxNzM2NDAzMzk3LCJVc2VySWQiOiJjODlmMWZlNy05MzIxLTRlNjItYmEwNy03NTk4NDRjOWE0M2EiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE3QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjJjZjVmMmU5LTBhZTUtNDE2NC05ZjYwLTQ5OThlOGYwYjNmNiIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.iWDkKAreqfPFnXOHtYvVxCJj1lQIi7UigXMNJeAc7Gw",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDMzOTcsImV4cCI6MTczNjY2MjU5NywiaWF0IjoxNzM2NDAzMzk3LCJVc2VySWQiOiJjODlmMWZlNy05MzIxLTRlNjItYmEwNy03NTk4NDRjOWE0M2EiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE3QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjJjZjVmMmU5LTBhZTUtNDE2NC05ZjYwLTQ5OThlOGYwYjNmNiIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.iWDkKAreqfPFnXOHtYvVxCJj1lQIi7UigXMNJeAc7Gw"));//emin.17@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDM0NzAsImV4cCI6MTczNjY2MjY3MCwiaWF0IjoxNzM2NDAzNDcwLCJVc2VySWQiOiI5YzY0Yjk3Ny03NDEzLTRjOGQtOGFiOC0zYjUxOWZlMWM3YTciLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE4QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6Ijg0NzdjYTU1LWI0YWQtNDQ1YS04YWJkLWI0ZTdiMmU3YTMwMSIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.PYxTMfT4LH3vOeUjxRLh76vVd1U4dHCznlM5nWuK_ic",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzY0MDM0NzAsImV4cCI6MTczNjY2MjY3MCwiaWF0IjoxNzM2NDAzNDcwLCJVc2VySWQiOiI5YzY0Yjk3Ny03NDEzLTRjOGQtOGFiOC0zYjUxOWZlMWM3YTciLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE4QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6Ijg0NzdjYTU1LWI0YWQtNDQ1YS04YWJkLWI0ZTdiMmU3YTMwMSIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.PYxTMfT4LH3vOeUjxRLh76vVd1U4dHCznlM5nWuK_ic"));//emin.18@atlasposta.com - Aa123456

        for (; ; ) {
            try {
                anaislem();
            } catch (Exception e) {
                System.out.println("HBSepet hata geldi");
            }
        }
    }

    private void anaislem() throws InterruptedException, IOException {
        HashMap<String, HbSepetUrunModel> urunHashMap = new HashMap<>();

        boolean ilkTur = true;
        for (;;) {
            List<HbSepetUrunModel> topluUrunList = new ArrayList<>();

            HashMap<String, HbSepetUrunModel> yeniUrunHashMap = new HashMap<>();
            for (HbTokenModel hbTokenModel: hbTokenHashMap.values()) {
                int urunSayisi = 0;
                List<HbSepetUrunModel> hbSepetUrunModelList = readJsonFromUrl(hbTokenModel);
                for (HbSepetUrunModel hbSepetUrunModel : hbSepetUrunModelList) {
                    if (hbSepetUrunModel.getPrice() == null || hbSepetUrunModel.getPrice().getAmount() == null || hbSepetUrunModel.getPrice().getAmount() == 0) {
                        continue;
                    }
                    urunSayisi++;
                    topluUrunList.add(hbSepetUrunModel);
                }
                hbTokenModel.setUrunSayisi(urunSayisi);
                hbTokenHashMap.put(hbTokenModel.getBearerTokent(), hbTokenModel);
            }

            for (HbSepetUrunModel hbSepetUrunModel : topluUrunList) {
                HbSepetUrunModel eskiHbUrunModel = urunHashMap.get(hbSepetUrunModel.getProduct().getSku());
                if (eskiHbUrunModel != null) {
                    //Önceden var olan ürün, fiyat kontrol et
                    if (hbSepetUrunModel.getPrice().getAmount() < eskiHbUrunModel.getPrice().getAmount() && !ilkTur) {
                        String mesaj = "" +
                                "İndirim\n" +
                                "" + hbSepetUrunModel.getProduct().getName() + "\n" +
                                "Eski Fiyat: " + eskiHbUrunModel.getPrice().getAmount() + "\n" +
                                "Yeni Fiyat: " + hbSepetUrunModel.getPrice().getAmount() + "\n" +
                                "Link:" + hbSepetUrunModel.getProduct().getUrl();

                        telegramMesajGonder(mesaj, "-4160976358");
                        telegramMesajGonder(mesaj, "-4101368331");
                    }
                } else {
                    if (!ilkTur) {
                        //Ürün yeni gelmiş direk mesaj at
                        String mesaj = "" +
                                "Yeni Ürün\n" +
                                "" + hbSepetUrunModel.getProduct().getName() + "\n" +
                                "Fiyat Fiyat: " + hbSepetUrunModel.getPrice().getAmount() + "\n" +
                                "Link:" + hbSepetUrunModel.getProduct().getUrl();

                        telegramMesajGonder(mesaj, "-4160976358");
                        telegramMesajGonder(mesaj, "-4101368331");
                    }
                }

                yeniUrunHashMap.put(hbSepetUrunModel.getProduct().getSku(), hbSepetUrunModel);
            }

            ilkTur = false;
            urunHashMap = yeniUrunHashMap;
        }
    }

    private void telegramMesajGonder(String mesaj, String chatId) throws IOException, InterruptedException {
        genelService.telegramMesajGonder(mesaj, chatId, "1", "5326840199:AAEWApODrhD-pxplBYAgUIRMo2FI565mNfM");
    }

    public List<HbSepetUrunModel> readJsonFromUrl(HbTokenModel hbTokenModel) throws IOException, JsonException {

        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("authorization", "Bearer " + hbTokenModel.getBearerTokent());
        headerMap.put("client-id", hbTokenModel.getClientId());
        headerMap.put("tenant-id", hbTokenModel.getTenantId());
        headerMap.put("Content-Type", "application/json; charset=UTF-8");
        headerMap.put("user-agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Mobile Safari/537.36");
        String json =
                Jsoup
                        .connect("https://checkout.hepsiburada.com/api/basket/all")
                        //.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36")
                        .headers(headerMap)
                        .ignoreContentType(true).execute().body();
        ObjectMapper mapper = new ObjectMapper();
        HbSepetGenel1Model hbSepetGenel1Model = mapper.readValue(json, HbSepetGenel1Model.class);
        return hbSepetGenel1Model.getResult().getBasket().getBasketItems();
    }
}
