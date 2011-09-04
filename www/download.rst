
Download
========

The current version is |version|. 
The library is licensed under the `GNU Lesser General Public License v3 <http://www.gnu.org/licenses/>`_.

Binary
------

Two binary packages are available. 
The complete package contains all classes, whereas the headless excludes all 
the GUI (swing) classes.

  * Complete: :sf-file:`jar <%(name)s-%(version)s.jar>`
  * Headless (no GUI): :sf-file:`jar <%(name)s-headless-%(version)s.jar>`
  
The headless package requires the following dependencies:

  * `Apache common math 2.2 <http://commons.apache.org/math/>`_
  * `opencsv <http://opencsv.sourceforge.net/>`_
  * `FindBugs <http://findbugs.sourceforge.net/>`_
  * `BeanShell <http://www.beanshell.org/>`_

The complete package also requires:

  * `MigLayout <http://www.miglayout.com/>`_

To run all the units tests:

  * `JUnit <http://www.junit.org/>`_
  * `UISpec4J <http://www.uispec4j.org/>`_

Ivy repository
--------------

.. highlight:: xml

The following resolver should be added to ivy settings to retrieve the 
jchemistry library::

  <url name="jchemistry">
      <ivy pattern="http://jchemistry.sf.net/ivy/ivy-[revision].xml" />
      <artifact pattern="http://jchemistry.sf.net/ivy/[artifact]-[revision].[ext]" />
  </url>
  
or the following to only include the **headless** version::

  <url name="jchemistry">
      <ivy pattern="http://jchemistry.sf.net/ivy/ivy-[revision].xml" />
      <artifact pattern="http://jchemistry.sf.net/ivy/[artifact]-headless-[revision].[ext]" />
  </url>

To combine this resolver with the default maven repository, use a chain resolver
in your :file:`ivysettings.xml`::

  <ivysettings>
    <settings defaultResolver="default"/>
    <resolvers>
      <chain name="default">
        <url name="jchemistry">
          <ivy pattern="http://jchemistry.sf.net/ivy/ivy-[revision].xml" />
          <artifact pattern="http://jchemistry.sf.net/ivy/[artifact]-[revision].[ext]" />
        </url>
        <ibiblio name="ibiblio" m2compatible="true" />
      </chain>
    </resolvers>
  </ivysettings>

Source
------

The source is available in a :sf-file:`zip <%(name)s-src-%(version)s.zip>` file 
or via the `Bazaar <http://bazaar.canonical.com>`_ repository on SourceForge:

  bzr://jchemistry.bzr.sourceforge.net/bzrroot/jchemistry
  

