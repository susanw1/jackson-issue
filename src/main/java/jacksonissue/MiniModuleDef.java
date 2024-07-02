package jacksonissue;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

public interface MiniModuleDef {

    interface ModuleModel {
        @JsonProperty(required = true)
        String getName();

        @JsonManagedReference
        List<CommandModel> getCommands();
    }

    interface CommandModel {
        @JsonProperty(required = true)
        String getName();

        @JsonBackReference
        ModuleModel getModule();

        TypeDefinition getTypeDefinition();
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
    @JsonSubTypes({
            @Type(value = EnumTypeDefinition.class, name = "enum"),
            @Type(value = NumberTypeDefinition.class, name = "number")
    })
    interface TypeDefinition {
    }

    interface EnumTypeDefinition extends TypeDefinition {
        List<String> getValues();
    }

    interface NumberTypeDefinition extends TypeDefinition {
    }
}
