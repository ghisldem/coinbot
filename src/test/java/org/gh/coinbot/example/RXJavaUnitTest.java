package org.gh.coinbot.example;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import io.reactivex.Observable;

public class RXJavaUnitTest {

	String result ="";
	
	@Test
	public void returnAValue() {
		result ="";
		Observable<String> observer = Observable.just("hello");
		observer.subscribe(s-> result = s);
		assertTrue(result.equals("hello"));
	}
	
}
