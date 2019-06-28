package com.example.zingdemoapi.typeadapter;

import com.example.zingdemoapi.datamodel.Artist;
import com.example.zingdemoapi.datamodel.Genre;
import com.example.zingdemoapi.datamodel.ProgramInfo;
import com.example.zingdemoapi.datamodel.Serie;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProgramInfoAdapter extends TypeAdapter<ProgramInfo> {
    @Override
    public void write(JsonWriter out, ProgramInfo value) throws IOException {

    }

    @Override
    public ProgramInfo read(JsonReader in) throws IOException {
        ProgramInfo programInfo = new ProgramInfo();
        in.beginObject();
        while (in.hasNext()){
            if (in.nextName().equals("data")){
                in.beginObject();
                while (in.hasNext()){
                    String name = in.nextName();
                    switch (name){
                        case "id":
                            programInfo.setId(in.nextInt());
                            break;
                        case "name":
                            programInfo.setName(in.nextString());
                            break;
                        case "thumbnail":
                            programInfo.setThumbnail(in.nextString());
                            break;
                        case "genres":
                            List<Genre> genres = new ArrayList<>();
                            GenreAdapter genreAdapter = new GenreAdapter();
                            in.beginArray();
                            while (in.hasNext()){
                                genres.add(genreAdapter.read(in));
                            }
                            in.endArray();
                            programInfo.setGenres(genres);
                            break;
                        case "description":
                            programInfo.setDescription(in.nextString());
                            break;
                        case "url":
                            programInfo.setUrl(in.nextString());
                            break;
                        case "has_sub_title":
                            programInfo.setHasSubTitle(in.nextBoolean());
                            break;
                        case "format":
                            programInfo.setFormat(in.nextString());
                            break;
                        case "listen":
                            programInfo.setListen(in.nextInt());
                            break;
                        case "comment":
                            programInfo.setComment(in.nextInt());
                            break;
                        case "rating":
                            programInfo.setRating(in.nextDouble());
                            break;
                        case "require_premium":
                            programInfo.setRequirePremium(in.nextBoolean());
                            break;
                        case "subscription":
                            programInfo.setSubscription(in.nextInt());
                            break;
                        case "is_subs":
                            programInfo.setIsSubs(in.nextBoolean());
                            break;
                        case "is_full_episode":
                            programInfo.setIsFullEpisode(in.nextBoolean());
                            break;
                        case "release_date":
                            programInfo.setReleaseDate(in.nextString());
                            break;
                        case "show_times":
                            programInfo.setShowTimes(in.nextString());
                            break;
                        case "pg":
                            programInfo.setPg(in.nextString());
                            break;
                        case "artists":
                            List<Artist> artists = new ArrayList<>();
                            ArtistAdapter artistAdapter = new ArtistAdapter();
                            in.beginArray();
                            while (in.hasNext()){
                                artists.add(artistAdapter.read(in));
                            }
                            in.endArray();
                            programInfo.setArtists(artists);
                            break;
                        case "duration":
                            programInfo.setDuration(in.nextString());
                            break;
                        case "cover":
                            programInfo.setCover(in.nextString());
                            break;
                        case "banner":
                            programInfo.setBanner(in.nextString());
                            break;
                        case "created_date":
                            programInfo.setCreatedDate(in.nextInt());
                            break;
                        case "modified_date":
                            programInfo.setModifiedDate(in.nextInt());
                            break;
                        case "series":
                            List<Serie> series = new ArrayList<>();
                            SerieAdapter serieAdapter = new SerieAdapter();
                            in.beginArray();
                            while (in.hasNext()){
                                series.add(serieAdapter.read(in));
                            }
                            in.endArray();
                            programInfo.setSeries(series);
                            break;
                        default:
                            in.skipValue();
                            break;

                    }

                }

                in.endObject();
            }
            else {
                in.skipValue();
            }
        }
        in.endObject();
        return programInfo;
    }
}
