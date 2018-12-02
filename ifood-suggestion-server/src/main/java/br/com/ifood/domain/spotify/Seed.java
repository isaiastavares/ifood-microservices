package br.com.ifood.domain.spotify;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Seed {

    @JsonProperty("initialPoolSize")
    private Integer initialPoolSize;
    
    @JsonProperty("afterFilteringSize")
    private Integer afterFilteringSize;
    
    @JsonProperty("afterRelinkingSize")
    private Integer afterRelinkingSize;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("href")
    private String href;

	public Integer getInitialPoolSize() {
		return initialPoolSize;
	}

	public void setInitialPoolSize(Integer initialPoolSize) {
		this.initialPoolSize = initialPoolSize;
	}

	public Integer getAfterFilteringSize() {
		return afterFilteringSize;
	}

	public void setAfterFilteringSize(Integer afterFilteringSize) {
		this.afterFilteringSize = afterFilteringSize;
	}

	public Integer getAfterRelinkingSize() {
		return afterRelinkingSize;
	}

	public void setAfterRelinkingSize(Integer afterRelinkingSize) {
		this.afterRelinkingSize = afterRelinkingSize;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
    
}
