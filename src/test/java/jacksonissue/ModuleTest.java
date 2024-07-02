package jacksonissue;

import java.io.IOException;
import java.net.URL;

import static java.util.Objects.requireNonNull;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.module.mrbean.MrBeanModule;
import org.junit.jupiter.api.Test;

public class ModuleTest {
    @Test
    public void shouldHandleTypeDefinitionJson() throws IOException {
        URL resourceurl = requireNonNull(getClass().getResource("/type-test.json"), "resourceStream");

        final JsonMapper                mapper = JsonMapper.builder().addModule(new MrBeanModule()).build();
        final MiniModuleDef.ModuleModel model  = mapper.readValue(resourceurl, MiniModuleDef.ModuleModel.class);

        checkModel(model, "MinimalTypeTestingJson");
    }

    @Test
    public void shouldHandleTypeDefinitionYaml() throws IOException {
        URL resourceurl = requireNonNull(getClass().getResource("/type-test.yaml"), "resourceStream");

        final JsonMapper                mapper = JsonMapper.builder(new YAMLFactory()).addModule(new MrBeanModule()).build();
        final MiniModuleDef.ModuleModel model  = mapper.readValue(resourceurl, MiniModuleDef.ModuleModel.class);

        checkModel(model, "MinimalTypeTestingYaml");
    }

    private static void checkModel(MiniModuleDef.ModuleModel model, String modelName) {
        assertThat(model.getName()).isEqualTo(modelName);
        assertThat(model.getCommands().get(0).getTypeDefinition()).isInstanceOf(MiniModuleDef.NumberTypeDefinition.class);
        assertThat(model.getCommands().get(1).getTypeDefinition()).isInstanceOf(MiniModuleDef.EnumTypeDefinition.class);
        assertThat(((MiniModuleDef.EnumTypeDefinition) model.getCommands().get(1).getTypeDefinition()).getValues()).containsExactly("foo", "bar");

        assertThat(((MiniModuleDef.EnumTypeDefinition) model.getCommands().get(2).getTypeDefinition()).getValues()).containsExactly("foo", "bar");
    }
}
