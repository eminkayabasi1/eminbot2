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
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTczNTMsImV4cCI6MTczNjYxNjU1MywiaWF0IjoxNzM2MzU3MzUzLCJVc2VySWQiOiI3Y2YxZDZmNS00NjhjLTRkZGEtOTlkZC0zNjNlZGVmODRlZjkiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjFAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiYmI2NTEwNzItMjIxYy00ODYzLWIxZGUtMzc1YzJhYTg4ODRhIiwicCI6eyJ0IjpbXSwiZSI6IitvMFJGODFnVklTM1kwa0s4aGxFSzJQRXlOSjBmMVBMMElHbkFucEJFME09In19.5Ncv-QgVChrIFDxAe5FElcziY4StDunohVxxttL03lg",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTczNTMsImV4cCI6MTczNjYxNjU1MywiaWF0IjoxNzM2MzU3MzUzLCJVc2VySWQiOiI3Y2YxZDZmNS00NjhjLTRkZGEtOTlkZC0zNjNlZGVmODRlZjkiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjFAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiYmI2NTEwNzItMjIxYy00ODYzLWIxZGUtMzc1YzJhYTg4ODRhIiwicCI6eyJ0IjpbXSwiZSI6IitvMFJGODFnVklTM1kwa0s4aGxFSzJQRXlOSjBmMVBMMElHbkFucEJFME09In19.5Ncv-QgVChrIFDxAe5FElcziY4StDunohVxxttL03lg"));//emin.1@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNjIzMjIsImV4cCI6MTczNjYyMTUyMiwiaWF0IjoxNzM2MzYyMzIyLCJVc2VySWQiOiI2ZGY0ZDA3YS1lZTExLTQ5NDMtYWQyNC05NjYxNjdlODkzYTIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjJAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiYjg5MTk5NmQtNWU4Mi00MWY5LTk0NzMtNWMyYzc2Nzg2NDAxIiwicCI6eyJ0IjpbXSwiZSI6IkVFcXcwVG5hNkxKTVBoRTNxcXVSTVdvNkttUWo1NjdLSVJ3N0JqZjlwRDA9In19.B_sSDOlm8RCYWlxsxeTjTVy1pxs-H0tcVTcQ3Me-IiQ",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNjIzMjIsImV4cCI6MTczNjYyMTUyMiwiaWF0IjoxNzM2MzYyMzIyLCJVc2VySWQiOiI2ZGY0ZDA3YS1lZTExLTQ5NDMtYWQyNC05NjYxNjdlODkzYTIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjJAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiYjg5MTk5NmQtNWU4Mi00MWY5LTk0NzMtNWMyYzc2Nzg2NDAxIiwicCI6eyJ0IjpbXSwiZSI6IkVFcXcwVG5hNkxKTVBoRTNxcXVSTVdvNkttUWo1NjdLSVJ3N0JqZjlwRDA9In19.B_sSDOlm8RCYWlxsxeTjTVy1pxs-H0tcVTcQ3Me-IiQ"));//emin.2@atlaspsota.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc0ODIsImV4cCI6MTczNjYxNjY4MiwiaWF0IjoxNzM2MzU3NDgyLCJVc2VySWQiOiJkZjQ3Y2UyZS05NjQ3LTRiNTYtOWU5Yi05NGE0ZWQ3ZDdkMmMiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjNAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZWI5YTk4ZTUtNjFkZC00NmIxLWIwNDQtZGQ3YTY4NWZhZmM2IiwicCI6eyJ0IjpbXSwiZSI6ImE2S3lNZDdxZmtEVEtRS3UxSkpxaDlJOHk5cEJ1ZDQ2Z1A1VWlZL1RqUWM9In19.MJqF6ZV-AzqU-aISAwLeamkmVRm_HKrBHrck_9caS4E",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc0ODIsImV4cCI6MTczNjYxNjY4MiwiaWF0IjoxNzM2MzU3NDgyLCJVc2VySWQiOiJkZjQ3Y2UyZS05NjQ3LTRiNTYtOWU5Yi05NGE0ZWQ3ZDdkMmMiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjNAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZWI5YTk4ZTUtNjFkZC00NmIxLWIwNDQtZGQ3YTY4NWZhZmM2IiwicCI6eyJ0IjpbXSwiZSI6ImE2S3lNZDdxZmtEVEtRS3UxSkpxaDlJOHk5cEJ1ZDQ2Z1A1VWlZL1RqUWM9In19.MJqF6ZV-AzqU-aISAwLeamkmVRm_HKrBHrck_9caS4E"));//emin.3@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc1NDIsImV4cCI6MTczNjYxNjc0MiwiaWF0IjoxNzM2MzU3NTQyLCJVc2VySWQiOiJhNjE2OGNlYy00ZmI0LTRlYmQtOTk2NS04ZWEyZDcyMDVjN2EiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjRAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiNDMzN2U1YzgtZmJkZS00ZWViLTgyYWEtODZiNzkxY2NhYjI1IiwicCI6eyJ0IjpbXSwiZSI6IkY2aTFvVmNZZzk5RWZZR09kTk4xRlo2UWhjd2t6UHRPNnJxQUxWb1dJVGs9In19.y8-1epQnwSAyc8W5_5JUCPG7ryP8029605GRlA56Rr8",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc1NDIsImV4cCI6MTczNjYxNjc0MiwiaWF0IjoxNzM2MzU3NTQyLCJVc2VySWQiOiJhNjE2OGNlYy00ZmI0LTRlYmQtOTk2NS04ZWEyZDcyMDVjN2EiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjRAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiNDMzN2U1YzgtZmJkZS00ZWViLTgyYWEtODZiNzkxY2NhYjI1IiwicCI6eyJ0IjpbXSwiZSI6IkY2aTFvVmNZZzk5RWZZR09kTk4xRlo2UWhjd2t6UHRPNnJxQUxWb1dJVGs9In19.y8-1epQnwSAyc8W5_5JUCPG7ryP8029605GRlA56Rr8"));//emin.4@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc1OTMsImV4cCI6MTczNjYxNjc5MywiaWF0IjoxNzM2MzU3NTkzLCJVc2VySWQiOiJiMWNkMWE0Mi1lMzBiLTQ5NzUtYmVlOC0yOTg5NDIxMjJjNmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjVAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZmY5ODBlYzktYzg3NS00MjU5LWJkMGUtZjM2MzM5MDgzYTczIiwicCI6eyJ0IjpbXSwiZSI6ImtFWjhsVnJFdEthZ20xZ1RvaFI0QXhGSFBDdWU4TkVaRXN5ckxQdmJjWXM9In19.OFKoljAGSz_a8qZfQANxJLu-nd4B9OTTDocNoQ3K7FY",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc1OTMsImV4cCI6MTczNjYxNjc5MywiaWF0IjoxNzM2MzU3NTkzLCJVc2VySWQiOiJiMWNkMWE0Mi1lMzBiLTQ5NzUtYmVlOC0yOTg5NDIxMjJjNmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjVAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiZmY5ODBlYzktYzg3NS00MjU5LWJkMGUtZjM2MzM5MDgzYTczIiwicCI6eyJ0IjpbXSwiZSI6ImtFWjhsVnJFdEthZ20xZ1RvaFI0QXhGSFBDdWU4TkVaRXN5ckxQdmJjWXM9In19.OFKoljAGSz_a8qZfQANxJLu-nd4B9OTTDocNoQ3K7FY"));//emin.5@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTY5ODIsImV4cCI6MTczNjYxNjE4MiwiaWF0IjoxNzM2MzU2OTgyLCJVc2VySWQiOiJkMDQwNWJlYy01YjFkLTQ0Y2MtOWI5OS1kYTJiOTRlNjJmNzEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjZAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiSnRpIjoiNzU3ZWVkOGEtODI2ZS00NWIxLWEyZmEtMzhlNjM0ZTdmNmRhIiwiVGVuYW50IjoiU1BBIiwicCI6eyJ0IjpbXX19.wvTeAqkISYR9TDXRtpdRYTjdkHFIqnnwyY9fbuFQ8h4",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTY5ODIsImV4cCI6MTczNjYxNjE4MiwiaWF0IjoxNzM2MzU2OTgyLCJVc2VySWQiOiJkMDQwNWJlYy01YjFkLTQ0Y2MtOWI5OS1kYTJiOTRlNjJmNzEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjZAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiSnRpIjoiNzU3ZWVkOGEtODI2ZS00NWIxLWEyZmEtMzhlNjM0ZTdmNmRhIiwiVGVuYW50IjoiU1BBIiwicCI6eyJ0IjpbXX19.wvTeAqkISYR9TDXRtpdRYTjdkHFIqnnwyY9fbuFQ8h4"));//emin.6@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgzNjEsImV4cCI6MTczNjYxNzU2MSwiaWF0IjoxNzM2MzU4MzYxLCJVc2VySWQiOiIyYTZhMTQ0OS05NTM3LTRkZGUtOGIwOC03ZDFiMWZiYzczNTAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjdAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiN2EwZTA5MGYtMjQwYy00N2ZhLWE0YTItY2VlOWQ4MGEzZjNjIiwicCI6eyJ0IjpbXSwiZSI6InVXeUFGRG5oOFNUWFpldmdQTDJQQi9VWDg3NzRMaXNCdldSNWN5WExDU0E9In19.3_AyWQaFmM-0si-MjvTHOf_pMxzQa59WRZhVCf8vwkc",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgzNjEsImV4cCI6MTczNjYxNzU2MSwiaWF0IjoxNzM2MzU4MzYxLCJVc2VySWQiOiIyYTZhMTQ0OS05NTM3LTRkZGUtOGIwOC03ZDFiMWZiYzczNTAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjdAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiVGVuYW50IjoiU1BBIiwiSnRpIjoiN2EwZTA5MGYtMjQwYy00N2ZhLWE0YTItY2VlOWQ4MGEzZjNjIiwicCI6eyJ0IjpbXSwiZSI6InVXeUFGRG5oOFNUWFpldmdQTDJQQi9VWDg3NzRMaXNCdldSNWN5WExDU0E9In19.3_AyWQaFmM-0si-MjvTHOf_pMxzQa59WRZhVCf8vwkc"));//emin.7@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTcxNDIsImV4cCI6MTczNjYxNjM0MiwiaWF0IjoxNzM2MzU3MTQyLCJVc2VySWQiOiIzNmQ3YTU3Mi00YjQ4LTQzMDUtOWE1YS03NWVlNDgxMzRmODciLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjhAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiSnRpIjoiMzQ2ZjRmMGUtM2E2Yy00MmU5LTlhYTMtNzZmNGJkNTUzNjUxIiwiVGVuYW50IjoiU1BBIiwicCI6eyJ0IjpbXX19.ZxizzutoXPxlPHx0yafyenEqFWDrxLBnAslb7XLNt8s",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTcxNDIsImV4cCI6MTczNjYxNjM0MiwiaWF0IjoxNzM2MzU3MTQyLCJVc2VySWQiOiIzNmQ3YTU3Mi00YjQ4LTQzMDUtOWE1YS03NWVlNDgxMzRmODciLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjhAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiSnRpIjoiMzQ2ZjRmMGUtM2E2Yy00MmU5LTlhYTMtNzZmNGJkNTUzNjUxIiwiVGVuYW50IjoiU1BBIiwicCI6eyJ0IjpbXX19.ZxizzutoXPxlPHx0yafyenEqFWDrxLBnAslb7XLNt8s"));//emin.8@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTcyMjQsImV4cCI6MTczNjYxNjQyNCwiaWF0IjoxNzM2MzU3MjI0LCJVc2VySWQiOiJhOGJkNjczYS04MDYzLTQ3MzItYmYxZi1kMGNlYzQ0YzMzZTgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjlAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiSnRpIjoiZWM5ZTMwM2UtMTU5Yy00YzMyLTgxMjEtNGMyOTg3Y2I3ODM2IiwiVGVuYW50IjoiU1BBIiwicCI6eyJ0IjpbXX19.pdvzkfDEpLrFODmgNCW9hGkysZS_QQMjvoA1vhlzMPY",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTcyMjQsImV4cCI6MTczNjYxNjQyNCwiaWF0IjoxNzM2MzU3MjI0LCJVc2VySWQiOiJhOGJkNjczYS04MDYzLTQ3MzItYmYxZi1kMGNlYzQ0YzMzZTgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjlAYXRsYXNwb3N0YS5jb20iLCJJc0F1dGhlbnRpY2F0ZWQiOiJUcnVlIiwiQXBwS2V5IjoiQUY3RjJBMzctQ0M0Qi00RjFDLTg3RkQtRkYzNjQyRjY3RUNCIiwiUHJvdmlkZXIiOiJIZXBzaWJ1cmFkYSIsIlNoYXJlRGF0YVBlcm1pc3Npb24iOiJUcnVlIiwiSnRpIjoiZWM5ZTMwM2UtMTU5Yy00YzMyLTgxMjEtNGMyOTg3Y2I3ODM2IiwiVGVuYW50IjoiU1BBIiwicCI6eyJ0IjpbXX19.pdvzkfDEpLrFODmgNCW9hGkysZS_QQMjvoA1vhlzMPY"));//emin.9@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTcyOTMsImV4cCI6MTczNjYxNjQ5MywiaWF0IjoxNzM2MzU3MjkzLCJVc2VySWQiOiI4OGFlYmViMi01MGIwLTRkNzUtYjZkMi04NzQ2ZTA1MmNkZWEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEwQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjExMDI4OWQ5LWFkY2QtNGU2NC1hYmEwLTQwM2VlMDBlODkyMiIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.RQ0N04MvHkTPRvB0SEYsqjqb8wCyv3v-8dyVvhQS_As",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTcyOTMsImV4cCI6MTczNjYxNjQ5MywiaWF0IjoxNzM2MzU3MjkzLCJVc2VySWQiOiI4OGFlYmViMi01MGIwLTRkNzUtYjZkMi04NzQ2ZTA1MmNkZWEiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEwQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjExMDI4OWQ5LWFkY2QtNGU2NC1hYmEwLTQwM2VlMDBlODkyMiIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.RQ0N04MvHkTPRvB0SEYsqjqb8wCyv3v-8dyVvhQS_As"));//emin.10@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc5NjIsImV4cCI6MTczNjYxNzE2MiwiaWF0IjoxNzM2MzU3OTYyLCJVc2VySWQiOiJlMGJlYTljYi05OWRiLTQ3NjktYWQzOC00ZmYyYmJiNTA1MmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjExQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjkxNDkwYjhlLWI4MzEtNGQ5Yy05MWRhLWRkN2M4NGQ3MzU5NyIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.7lbf88jxmXhDO2h3neFmMhWJ1C79BdZ-pToNghDykf4",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTc5NjIsImV4cCI6MTczNjYxNzE2MiwiaWF0IjoxNzM2MzU3OTYyLCJVc2VySWQiOiJlMGJlYTljYi05OWRiLTQ3NjktYWQzOC00ZmYyYmJiNTA1MmIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjExQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjkxNDkwYjhlLWI4MzEtNGQ5Yy05MWRhLWRkN2M4NGQ3MzU5NyIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.7lbf88jxmXhDO2h3neFmMhWJ1C79BdZ-pToNghDykf4"));//emin.11@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgwODAsImV4cCI6MTczNjYxNzI4MCwiaWF0IjoxNzM2MzU4MDgwLCJVc2VySWQiOiJhZjZlYmU0ZC1hZjQyLTQ5YTAtYWZjZS1hYWUwMTZmMWI4YjAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEyQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjU0YWUzYjFlLTU5NTUtNGY1NC05YmVkLWE3YzEzY2Q4MmZhZSIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.zZRF1xPb6WqKU49NnppKxMkjmqDwiWl64vCEKSMir_4",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgwODAsImV4cCI6MTczNjYxNzI4MCwiaWF0IjoxNzM2MzU4MDgwLCJVc2VySWQiOiJhZjZlYmU0ZC1hZjQyLTQ5YTAtYWZjZS1hYWUwMTZmMWI4YjAiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEyQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjU0YWUzYjFlLTU5NTUtNGY1NC05YmVkLWE3YzEzY2Q4MmZhZSIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.zZRF1xPb6WqKU49NnppKxMkjmqDwiWl64vCEKSMir_4"));//emin.12@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgxNzEsImV4cCI6MTczNjYxNzM3MSwiaWF0IjoxNzM2MzU4MTcxLCJVc2VySWQiOiIzODY2MTI1Zi0zMWIyLTQ1MzgtOThkZS01NDlhYmYxODUyNDgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEzQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6ImYzZDA1OTFjLTA2NWYtNDJlNC1hODJjLTk1YmZjMWQyYzNiNiIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.pNMxHiGd2ZVxolqwmrFyHeH6JXRXCRWW_hb2KYT7RQg",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgxNzEsImV4cCI6MTczNjYxNzM3MSwiaWF0IjoxNzM2MzU4MTcxLCJVc2VySWQiOiIzODY2MTI1Zi0zMWIyLTQ1MzgtOThkZS01NDlhYmYxODUyNDgiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjEzQGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6ImYzZDA1OTFjLTA2NWYtNDJlNC1hODJjLTk1YmZjMWQyYzNiNiIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.pNMxHiGd2ZVxolqwmrFyHeH6JXRXCRWW_hb2KYT7RQg"));//emin.13@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgyMzcsImV4cCI6MTczNjYxNzQzNywiaWF0IjoxNzM2MzU4MjM3LCJVc2VySWQiOiJmM2M4NzY2Zi04NjgzLTQ0MmMtOGZiYi1mODc1YTZmOWRkNzIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE0QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6ImQzNGQ5YTY4LWUyMTQtNDE4Ny1iZTI2LTQ1MDA3NmU5NGExOCIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.HAgYyBS6aMDK1nTH5-Lmf-6VJVSSrPCRP6cal_8OdLc",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgyMzcsImV4cCI6MTczNjYxNzQzNywiaWF0IjoxNzM2MzU4MjM3LCJVc2VySWQiOiJmM2M4NzY2Zi04NjgzLTQ0MmMtOGZiYi1mODc1YTZmOWRkNzIiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE0QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6ImQzNGQ5YTY4LWUyMTQtNDE4Ny1iZTI2LTQ1MDA3NmU5NGExOCIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.HAgYyBS6aMDK1nTH5-Lmf-6VJVSSrPCRP6cal_8OdLc"));//emin.14@atlasposta.com - Aa123456
        hbTokenHashMap.put(
                "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgzMTQsImV4cCI6MTczNjYxNzUxNCwiaWF0IjoxNzM2MzU4MzE0LCJVc2VySWQiOiIwNjVlYTA3ZS0wNTBmLTRlOTgtOGQ2NS05OGQ0ZjYyMWRjY2IiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE1QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjhlYTViMjQwLTgyYWQtNGUwZS05MjI5LWY2YmQ4MWRjMWFhMyIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.6nhfldobBdWUZqd1Qkl7RE4E4sr4C4bgRxON3fxIKHg",
                new HbTokenModel("47b14cfb-2cad-471a-85de-ebe4684ee95f", "cc7c5241-6017-44b2-9528-93c8d8907efb", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJuYmYiOjE3MzYzNTgzMTQsImV4cCI6MTczNjYxNzUxNCwiaWF0IjoxNzM2MzU4MzE0LCJVc2VySWQiOiIwNjVlYTA3ZS0wNTBmLTRlOTgtOGQ2NS05OGQ0ZjYyMWRjY2IiLCJUaXRsZSI6IkVtaW4gQ2FuIiwiRmlyc3ROYW1lIjoiRW1pbiIsIkxhc3ROYW1lIjoiQ2FuIiwiRW1haWwiOiJlbWluLjE1QGF0bGFzcG9zdGEuY29tIiwiSXNBdXRoZW50aWNhdGVkIjoiVHJ1ZSIsIkFwcEtleSI6IkFGN0YyQTM3LUNDNEItNEYxQy04N0ZELUZGMzY0MkY2N0VDQiIsIlByb3ZpZGVyIjoiSGVwc2lidXJhZGEiLCJTaGFyZURhdGFQZXJtaXNzaW9uIjoiVHJ1ZSIsIkp0aSI6IjhlYTViMjQwLTgyYWQtNGUwZS05MjI5LWY2YmQ4MWRjMWFhMyIsIlRlbmFudCI6IlNQQSIsInAiOnsidCI6W119fQ.6nhfldobBdWUZqd1Qkl7RE4E4sr4C4bgRxON3fxIKHg"));//emin.15@atlasposta.com - Aa123456

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
