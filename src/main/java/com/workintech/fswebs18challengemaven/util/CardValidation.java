package com.workintech.fswebs18challengemaven.util;

import com.workintech.fswebs18challengemaven.entity.Card;
import com.workintech.fswebs18challengemaven.entity.Type;
import com.workintech.fswebs18challengemaven.exceptions.CardException;
import org.springframework.http.HttpStatus;

public class CardValidation {
    public static void validateCard(Card card) {
        if(card.getType() != null && card.getValue() != null && card.getValue()>0){
            throw new CardException("Bir kartın hem type hem de value değeri olamaz.", HttpStatus.BAD_REQUEST);
        }
        if(card.getType() != null && card.getType().equals(Type.JOKER) && ((card.getColor() != null) || (card.getValue() != null && card.getValue()>0))){
            throw new CardException("Kart tipi JOKER ise hem value hem de color null değer almalıdır.", HttpStatus.BAD_REQUEST);
        }
    }
}
