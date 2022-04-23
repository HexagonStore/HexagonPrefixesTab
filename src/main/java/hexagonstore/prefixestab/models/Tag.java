package hexagonstore.prefixestab.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class Tag {

    private String id, groupID, prefix, suffix, position;
}
