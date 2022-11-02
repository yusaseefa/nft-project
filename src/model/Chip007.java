package model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Chip007 {

    public Chip007() {
        this.format = "CHIP-0007";
        this.mintingTool = "Team Bevel";
        this.seriesTotal = 526;
        this.sensitiveContent = false;
    }

    private String format;
    private String name;
    private String description;

    @JsonProperty("minting_tool")
    private String mintingTool;

    @JsonProperty("sensitive_content")
    private boolean sensitiveContent;

    @JsonProperty("series_number")
    private int seriesNumber;

    @JsonProperty("series_total")
    private int seriesTotal;

    private List<Attribute> attributes = new ArrayList<>();
    private Collection collection = new Collection();
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attribute {

        @JsonProperty("trait_type")
        private String traitType;
        private String value;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Collection {

        public Collection() {
            this.name = "Zuri NFT Tickets for Free Lunch";
        }
        private String name;
        private String id;
        private List<CollectionAttr> attributes = new ArrayList<>();

    }
    @Getter
    @Setter
    @AllArgsConstructor
    public static class CollectionAttr {
        private String type;
        private String value;

        public CollectionAttr() {
            this.type = "description";
            this.value = "Rewards for accomplishments during HNGi9.";
        }

    }

}
