//package com.i9.complience.utility;
//
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//public class Test {
//
//	public static void main(String[] args) {
//
//		// 3, 4, 9
//		List<Integer> list = Arrays.asList(5, 3, 4, 1, 3, 7, 2, 9, 9, 4);
//
//		Set<Integer> result = findDuplicateByFrequency(list);
//
//		result.forEach(System.out::println);
//
//	}
//
//	public static <T> Set<T> findDuplicateByFrequency(List<T> list) {
//		Set<T> items = new HashSet<>();
//        return list.stream()
//                .filter(n -> !items.add(n)) // Set.add() returns false if the element was already in the set.
//                .collect(Collectors.toSet());
//
//		//return list.stream().filter(i -> Collections.frequency(list, i) < 1).collect(Collectors.toSet());
//
//	}
//
//}
