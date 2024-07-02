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

    /**
     * Most components of the Model have a name/description/longDescription - this defines them in one place.
     */
    interface ModelComponent {
        @JsonProperty(required = true)
        String getName();
    }

    interface ModuleModel extends ModelComponent {
        @JsonManagedReference
        List<CommandModel> getCommands();
    }

    interface CommandModel extends ModelComponent {
        @JsonBackReference
        ModuleModel getModule();

        TypeDefinition getTypeDefinition();
    }

    @JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class)
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, defaultImpl = TypeDefinition.class)
    @JsonSubTypes({
            @Type(value = EnumTypeDefinition.class, name = "enum"),
            @Type(value = NumberTypeDefinition.class, name = "number")
    })
    interface TypeDefinition {
    }

    interface EnumTypeDefinition extends TypeDefinition {
        default boolean enumType() {
            return true;
        }

        List<String> getValues();
    }

    interface NumberTypeDefinition extends TypeDefinition {
        default boolean numberType() {
            return true;
        }
    }
}
