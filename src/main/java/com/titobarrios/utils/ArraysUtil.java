package com.titobarrios.utils;

import java.util.ArrayList;
import java.util.Arrays;

import com.titobarrios.error.ElementNotFoundException;

public class ArraysUtil {
    @SuppressWarnings("unchecked")
	public static <T> T[] combineArrays(T[]... arrays){
		ArrayList<T> combined = new ArrayList<T>();
		for(T[] array : arrays) {
			combined.addAll(Arrays.asList(array));
		}
		return combined.toArray(Arrays.copyOf(arrays[0], combined.size()));
	}

	public static <T> T searchElement(T[] list, T element) throws ElementNotFoundException {
		for(T current : list)
			if(element.equals(current)) return element;
			throw new ElementNotFoundException("The element: " + element + " doesn't exists on the list " + list);
	}

	public static <T> int searchElementSlot(T[] list, T element) throws ElementNotFoundException {
		for(int i = 0; i < list.length; i++)
			if(list[i].equals(element)) return i;
		throw new ElementNotFoundException("The element: " + element + " doesn't exists on the list " + list);
	}

	@SuppressWarnings("unchecked")
	public static <T> void deleteSlot(T[] list, int slot) {
		T[] newList = (T[]) new Object[list.length - 1];
		for (int i = 0; i < list.length; i++)
			if (i != slot) newList[i] = list[i];
		list = newList;
	}

	@SuppressWarnings("unchecked")
	public static <T> void deleteSlot(T[] list, T element) {
		T[] newList = (T[]) new Object[list.length - 1];
		for (int i = 0; i < list.length; i++)
			if (!list[i].equals(element)) newList[i] = list[i];
		list = newList;
	}
}
