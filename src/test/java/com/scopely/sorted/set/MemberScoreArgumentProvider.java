package com.scopely.sorted.set;

import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

public class MemberScoreArgumentProvider implements ArgumentsProvider {
  @Override
  public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
    return Stream.of(Arguments.of(Stream.generate(this::randomMemberScore)));
  }

  private MemberScore randomMemberScore() {
    String member = UUID.randomUUID().toString();
    double score = Math.random();
    return new MemberScore(member, score);
  }
}
