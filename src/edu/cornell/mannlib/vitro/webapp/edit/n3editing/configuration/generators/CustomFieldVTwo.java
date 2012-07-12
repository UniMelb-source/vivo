package edu.cornell.mannlib.vitro.webapp.edit.n3editing.configuration.generators;

import edu.cornell.mannlib.vitro.webapp.edit.n3editing.VTwo.FieldVTwo;
import java.util.List;

/**
 *
 * @author tom
 */
public class CustomFieldVTwo extends FieldVTwo {

    public CustomFieldVTwo(String fieldName,
            List<String> validators,
            String rangeDatatypeUri,
            FieldVTwo.OptionsType optionsType,
            List<List<String>> literalOptions,
            String objectClassUri) {
        super();
        setName(fieldName);
        if (validators != null) {
            setValidators(validators);
        }
        if (rangeDatatypeUri != null) {
            setRangeDatatypeUri(rangeDatatypeUri);
        }
        if (optionsType != null) {
            setOptionsType(optionsType);
        }
        if (literalOptions != null) {
            setLiteralOptions(literalOptions);
        }
        if (objectClassUri != null) {
            setObjectClassUri(objectClassUri);
        }
    }
}
