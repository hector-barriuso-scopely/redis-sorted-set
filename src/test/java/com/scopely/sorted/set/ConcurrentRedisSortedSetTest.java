package com.scopely.sorted.set;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

public class ConcurrentRedisSortedSetTest {

  private ConcurrentRedisSortedSet classUnderTest;

  @BeforeEach
  public void setUp() {
    classUnderTest = new ConcurrentRedisSortedSet();
  }

  @Test
  public void shouldAddAMemberToTheSet() {
    int actual = classUnderTest.add("member", 100);
    assertThat(actual).isEqualTo(1);
  }

  @ParameterizedTest(name = "shouldReturnARangeOfTheSet()")
  @ArgumentsSource(value = MemberScoreArgumentProvider.class)
  public void shouldReturnARangeOfTheSet(Stream<MemberScore> memberScores) {
    memberScores
        .limit(10)
        .forEach(
            memberScore -> classUnderTest.add(memberScore.getMember(), memberScore.getScore()));
    List<String> actual = classUnderTest.range(3, 7);
    assertThat(actual.size()).isEqualTo(4);
  }

  @ParameterizedTest(name = "shouldReturnTheRankOfAMember()")
  @ArgumentsSource(value = MemberScoreArgumentProvider.class)
  public void shouldReturnTheRankOfAMember(Stream<MemberScore> memberScores) {
    memberScores
        .limit(10)
        .forEach(
            memberScore -> classUnderTest.add(memberScore.getMember(), memberScore.getScore()));
    classUnderTest.add("member", -1);
    int actual = classUnderTest.rank("member");
    assertThat(actual).isEqualTo(10);
  }

  @ParameterizedTest(name = "shouldReturnTheRankOfAnotherMember()")
  @ArgumentsSource(value = MemberScoreArgumentProvider.class)
  public void shouldReturnTheRankOfAnotherMember(Stream<MemberScore> memberScores) {
    memberScores
        .limit(100_000)
        .forEach(
            memberScore -> classUnderTest.add(memberScore.getMember(), memberScore.getScore()));
    classUnderTest.add("member", 1);
    int actual = classUnderTest.rank("member");
    assertThat(actual).isEqualTo(0);
  }
}
