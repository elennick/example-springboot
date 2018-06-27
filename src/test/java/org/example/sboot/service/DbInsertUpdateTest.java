package org.example.sboot.service;

import io.ebean.EbeanServer;
import org.example.sboot.domain.Content;
import org.example.sboot.domain.repo.ContentRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@SpringBootTest
public class DbInsertUpdateTest {

  @Autowired
  EbeanServer server;

  @Autowired
  ContentRepository contentRepository;

  @Test
  public void testInsertUpdate() {

    assertNotNull(server);

    // -------------------------------------------------------------
    // Model and Finder style ...

    Content foo = new Content();
    foo.setName("foo");
    foo.save();
    server.save(foo);

    Content fooFound = server.find(Content.class).where().eq("name", "foo").findOne();
    assertThat(foo.getName()).isEqualTo(fooFound.getName());

    foo.setName("moo");
    foo.save();

    fooFound = server.find(Content.class).where().eq("name", "moo").findOne();
    assertThat(foo.getName()).isEqualTo(fooFound.getName());

    Content fetchFoo = Content.find.byId(foo.getId());
    fetchFoo.setName("boo");
    fetchFoo.save();

    fooFound = server.find(Content.class).where().eq("name", "boo").findOne();
    assertThat(fetchFoo.getName()).isEqualTo(fooFound.getName());

    // -------------------------------------------------------------
    // Repository style ...

    Content baz = new Content();
    baz.setName("baz");
    contentRepository.save(baz);

    Content bazFound = server.find(Content.class).where().eq("name", "baz").findOne();
    assertThat(baz.getName()).isEqualTo(bazFound.getName());

    Content fetchBaz = contentRepository.byId(baz.getId());
    fetchBaz.setName("bazza");
    contentRepository.save(fetchBaz);

    bazFound = server.find(Content.class).where().eq("name", "bazza").findOne();
    assertThat(fetchBaz.getName()).isEqualTo(bazFound.getName());

  }
}