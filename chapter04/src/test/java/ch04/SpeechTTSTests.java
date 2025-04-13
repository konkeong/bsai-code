package ch04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ch04.service.TextToSpeechService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class SpeechTTSTests {

    @Autowired
    protected TextToSpeechService textToSpeechService;

    @Test
    public void runTTSQuery() throws IOException {
        // https://www.azlyrics.com/lyrics/michaeljackson/maninthemirror.html
        String lyricManInTheMirror = """
                I'm gonna make a change
                For once in my life
                It's gonna feel real good
                Gonna make a difference
                Gonna make it right

                As I turn up the collar on
                My favorite winter coat
                This wind is blowing my mind
                I see the kids in the street
                With not enough to eat
                Who am I to be blind
                Pretending not to see their needs?

                A summer's disregard
                A broken bottle top
                And one man's soul
                They follow each other on the wind you know
                'Cause they got nowhere to go
                That's why I want you to know

                I'm starting with the man in the mirror
                I'm asking him to change his ways
                And no message could have been any clearer
                If you wanna make the world a better place
                Take a look at yourself and then make a change

                Na-na-na na-na-na nana-nana

                I've been a victim of
                A selfish kind of love
                It's time that I realize
                There are some with no home
                Not a nickel to loan
                Could it be really me
                Pretending that they're not alone?

                A willow deeply scarred
                Somebody's broken heart
                And a washed-out dream
                (Washed-out dream)
                They follow the pattern of the wind
                You see
                'Cause they got no place to be
                That's why I'm starting with me

                I'm starting with the man in the mirror (Who?)
                I'm asking him to change his ways (Who?)
                And no message could have been any clearer
                If you wanna make the world a better place
                Take a look at yourself and then make a change

                I'm starting with the man in the mirror (Who?)
                I'm asking him to change his ways (Who?)
                And no message could have been any clearer
                If you wanna make the world a better place
                Take a look at yourself and then make that change

                I'm starting with the man in the mirror (Man in the mirror, oh yeah)
                I'm asking him to change his ways
                ('Cause you better change!)
                No message could have been any clearer
                If you wanna make the world a better place
                Take a look at yourself and then make the change

                You gotta get it right
                While you got the time
                'Cause when you close your heart

                You can't close your your mind
                (Then you close your mind!)

                That man, that man, that man, that man
                With the man in the mirror
                (Man in the mirror, oh yeah!)
                That man, that man, that man
                I'm asking him to change his ways
                (Better change!)
                You know that man
                No message could have been any clearer
                If you wanna make the world a better place
                Take a look at yourself and then make a change

                Hoo, hoo (Na-na-na na-na-na nana-nana), hoo, hoo, hoo, hoo, hoo!

                Gonna feel real good (Oh yeah)
                Yeah, yeah, yeah, yeah (Na-na-na na-na-na nana-nana)

                Oh, no, oh, no

                I'm gonna make a change
                It's gonna feel real good
                Shamone
                Change

                Just lift yourself
                You know
                You've got to stop it yourself
                (Yeah! Hoo! Make that change!)
                I gotta make that change today, hoo
                (Man in the mirror)
                You got to
                You got to not pick yourself, brother, hoo
                (Yeah! Make that change!)
                You know
                I gotta get that man, that man
                (Man in the mirror)
                You've got to
                You've got to move
                Shamone, shamone
                You got to stand up! Stand up! Stand up!
                (Yeah. Make that change)
                Stand up and lift yourself now
                (Man in the mirror)

                Hoo, hoo, hoo! Aaow!

                (Yeah. Make that change)
                Gonna make that change
                Shamone
                (Man in the mirror)

                You know it
                You know it
                You know it
                You know

                Change
                Make that change
                """;

        byte[] responseAsBytes = textToSpeechService.synthesize(lyricManInTheMirror);
        Assertions.assertNotNull(responseAsBytes);

        Path path = Paths.get("./target/man_in_the_mirror.mp3");
        System.out.println("generated " + path.toAbsolutePath().toString());
        Files.write(path, responseAsBytes);
    }

}
