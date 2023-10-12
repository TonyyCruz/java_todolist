package br.com.anthonycruz.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {

  /**
   * Mapeia os valoresdas das propiedades do objeto "source" que são nulos
   * e os preenche no objeto "target" que possuam o mesmo nome.
   * 
   * @param source
   * @param target
   */
  public static void copyNotNullProperties(Object source, Object target) {
    BeanUtils.copyProperties(source, target, getNullPropertiesNames(source));
  }

  /**
   * Interage com as propriedades do objeto e caso seu valor seja nulo
   * adiciona o nome da propriedade em uma lista.
   * 
   * @param source
   * @return [propriedade_nula_1, propriedade_nula_2]
   */
  public static String[] getNullPropertiesNames(Object source) {
    final BeanWrapper src = new BeanWrapperImpl(source);

    PropertyDescriptor[] pds = src.getPropertyDescriptors();

    Set<String> emptyNames = new HashSet<String>();

    for (PropertyDescriptor pd : pds) {
      Object srcValue = src.getPropertyValue(pd.getName());

      if (srcValue == null) {
        emptyNames.add(pd.getName());
      }
    }

    String[] result = new String[emptyNames.size()];

    return emptyNames.toArray(result);
  }
}
