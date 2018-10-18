package boosterm.backend.api.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.List;

@JsonNaming(SnakeCaseStrategy.class)
public class SourcesResponse {
	
	private int analysedElements;
	
	private List<SourceResponse> sources;
	
    public SourcesResponse(int analysedElements, List<SourceResponse> sources) {
    	this.analysedElements = analysedElements;
    	this.sources = sources;
    }
	
    public int getAnalysedElements() {
        return analysedElements;
    }

    public List<SourceResponse> getSources() {
        return sources;
    }
}
