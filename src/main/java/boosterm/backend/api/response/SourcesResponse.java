package boosterm.backend.api.response;

import java.util.List;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

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
