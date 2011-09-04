
.. highlight:: java

Examples
========

The main class of the chemistry library is the enum class 
`Element <javadoc/net/sf/jchemistry/core/Element.html>`_. 
All elements can be accessed via their symbol, e.g.::

  Element.Au;
  Element.Be;

Or static method are available to retrieve the enum elements::

  Element.fromSymbol("au"); 
  Element.fromZ(4);
  
With the enum elements, some physical, atomic and other properties can be
obtained in the class `ElementProperties <javadoc/net/sf/jchemistry/core/ElementProperties.html>`_::

  ElementProperties.getAtomicMass(Element.Au); // 196.967 g/mol
  ElementProperties.getDensity(Element.Be); // 1.85 kg/m3
  
--------------------------

Another part of the jchemistry library is the crystallography related classes.
The main class is a `Phase <javadoc/net/sf/jchemistry/crystallography/core/Phase.html>`_ 
which stores information about a crystallography phase:

  * name
  * space group
  * unit cell
  * atom positions
  * reflectors (diffracting plane)

Here is an example how to create a silicon phase.
First let's define the unit cell::

  UnitCell unitCell = UnitCellFactory.cubic(5.43); // 5.43 angstroms

The `UnitCellFactory <javadoc/net/sf/jchemistry/crystallography/core/UnitCellFactory.html>`_ 
class contains a method for each of the 7 types of crystal systems.

All the 230 space groups are defined in the class 
`SpaceGroups <javadoc/net/sf/jchemistry/crystallography/core/SpaceGroups.html>`_.
They can be retrieved directly or from their index or symbol::

  SpaceGroup sg = SpaceGroups.SG216;

or::

  SpaceGroup sg = SpaceGroups.fromIndex(216);
  
or::

  SpaceGroup sg = SpaceGroups.fromSymbol("F-43m");
  
With these informations, a 
`Phase <javadoc/net/sf/jchemistry/crystallography/core/Phase.html>`_ 
can be created as follows::

  Phase phase = new Phase("Silicon", sg, unitCell);

Then the atom positions
(`AtomSite <javadoc/net/sf/jchemistry/crystallography/core/AtomSite.html>`_)
can be added.
An atom position is defined with:

  * element of the atom
  * electric charge
  * position (fraction of the unit cell a, b and c lattice constants)
  * occupancy

Here is how to add an atom::

  AtomSite atom = new AtomSite(Element.Si, 0, new Vector3D(0, 0, 0), 1.0);
  phase.getAtoms().add(atom);

The equivalent position for the atom positions are automatically calculated
inside the `Phase <javadoc/net/sf/jchemistry/crystallography/core/Phase.html>`_ 
class.
As such, after adding the atom at (0, 0, 0), the other atoms of the FCC unit
cell of the silicon phase were automatically generated::

  for(AtomSite atom: phase.getAtoms()) 
      System.out.println(atom.getPosition());
  
  // 0.0, 0.0, 0.0
  // 0.0, 0.5, 0.5
  // 0.5, 0.0, 0.5
  // 0.5, 0.5, 0.0

The diffracting crystallographic planes 
(`Reflector <javadoc/net/sf/jchemistry/crystallography/core/Reflector.html>`_) 
can be setup in the phase
in a similar fashion as the atom positions.
Here is how to add a reflector with *hkl* indices (1, 1, 1) and diffracting
intensity of 0.5::

  Reflector reflector = new Reflector(1, 1, 1, 0.5);
  phase.getReflectors().add(reflector);

The reflectors can also be calculated directly from the scattering factors.
Three data sets of scattering factors are available:

  * Tabulated values for electron scattering 
  * Tabulated values for X-ray scattering
  * Electron scattering values obtained by the Mott-Bethe equation

The tabulated values are obtained from the fitted equation available in the
International Tables for Crystallography. 
For more information about the calculations and references please refer to the
`javadoc <javadoc/net/sf/jchemistry/crystallography/core/ScatteringFactorsFactory.html>`_.

The reflectors are computed by specifying the scattering factors, the maximum 
index of the diffracting planes to compute and the minimum intensity to say that
a plane is diffracting.
In the following example, the diffraction intensity of all planes with indices 
up to 4 (e.g. (4, 4, 4)) is calculated. 
Only those with an intensity greater than 1% of the maximal intensity are
stored as reflectors.::

  phase.computeReflectors(ScatteringFactorsFactory.ELECTRON_TABULATED, 4, 0.01);
  

Finally, all the information about this phase can be saved in a CIF file::

  try {
      FileWriter out = new FileWriter(new File("silicon.cif"));
      new CifSaver().save(out, phase); 
  } catch (IOException ex) {
      ex.printStackTrace();
  }

and loaded back to a phase::

  try {
      FileReader in = new FileReader(new File("silicon.cif"));
      Phase phase = new CifLoader().load(in);
  } catch (IOException ex) {
      ex.printStackTrace();
  }
