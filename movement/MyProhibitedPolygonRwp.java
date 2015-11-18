package movement;

import core.Coord;
import core.Settings;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Random Waypoint Movement with a prohibited region where nodes may not move
 * into. The polygon is defined by a *closed* (same point as first and
 * last) path, represented as a list of {@code Coord}s.
 *
 * @author teemuk, nick promponas
 */
public class MyProhibitedPolygonRwp
        extends MapBasedMovement {

  //==========================================================================//
  // Settings
  //==========================================================================//
  /** {@code true} to confine nodes inside the polygon */
  public static final String INVERT_SETTING = "rwpInvert";
  public static final boolean INVERT_DEFAULT = true;
  //==========================================================================//


  //==========================================================================//
  // Instance vars
  //==========================================================================//
  final List <Coord> polygon = initializePolygon();

  private Coord lastWaypoint;
  /** Inverted, i.e., only allow nodes to move inside the polygon. */
  private final boolean invert;

  //private Coord minBound;
  //private Coord maxBound;
  private int localMaxX;
  private int localMaxY;
    private boolean goToLecture = false;
  //==========================================================================//



  //==========================================================================//
  // Implementation
  //==========================================================================//
//  @Override
//    public Path getPath() {
//        // Creates a new path from the previous waypoint to a new one.
//        if(goToLecture) {
//            return null;
//        }
//
//        final Path p;
//        p = new Path( super.generateSpeed() );
//        p.addWaypoint( this.lastWaypoint.clone() );
//
//        // Add only one point. An arbitrary number of Coords could be added to
//        // the path here and the simulator will follow the full path before
//        // asking for the next one.
//        Coord c;
//        do {
//            //If I can reach lecture place in double the time from the current position, can check another position.
//            Coord lectureCoord = new Coord(475.0,140.0);
//            Random rng = new Random();
//            switch (rng.nextInt(2)) {
//                case 0:
//                    lectureCoord = new Coord(475.0,140.0);
//                    break;
//                case 1:
//                    lectureCoord = new Coord(440.0,200.0);
//                    break;
//            }
//
//
//            if(2000 < core.SimClock.getTime() && core.SimClock.getTime() < 4000 ){
//                c = lectureCoord;
//                goToLecture = true;
//                this.host.setOnTheWayToALecture(true);
//            }else{
//                c = this.randomCoord();
//            }
//        /*double timeToLecture = 3000.0 - core.SimClock.getTime();
//        double possibleMovement = timeToLecture * super.generateSpeed();
//
//        Coord lectureCoord = new Coord(475.0,140.0);
//        double distance = host.getLocation().distance(lectureCoord);
//        if (possibleMovement >= 2*distance)
//            c = this.randomCoord();
//        else{
//            c = lectureCoord;
//            goToLecture = true;
//        }*/
//        } while ( pathIntersects( this.polygon, this.lastWaypoint, c ) );
//        p.addWaypoint( c );
//
//        this.lastWaypoint = c;
//        return p;
//    }
    @Override
    public Path getPath() {
        // Creates a new path from the previous waypoint to a new one.

        final Path p;
        p = new Path( super.generateSpeed() );
        p.addWaypoint( this.lastWaypoint.clone() );

//        // Add only one point. An arbitrary number of Coords could be added to
//        // the path here and the simulator will follow the full path before
//        // asking for the next one.
//        Coord c;
//        do {
//                c = this.randomCoord();
//        } while ( pathIntersects( this.polygon, this.lastWaypoint, c ) );

        Coord c = host.getState().getDestination();
        p.addWaypoint( c );

        this.lastWaypoint = c;
        return p;
    }
  @Override
  public Coord getInitialLocation() {
//    do {
      this.lastWaypoint = new Coord(
              rng.nextDouble() * super.getMaxX(),
              rng.nextDouble() * super.getMaxY());
//    } while ( ( this.invert ) ?
//            isOutside( polygon, this.lastWaypoint ) :
//            isInside( this.polygon, this.lastWaypoint ) );
    return this.lastWaypoint;
  }

  @Override
  public MapBasedMovement replicate() {
    return new MyProhibitedPolygonRwp( this );
  }

  public Coord randomCoord() {

    // Get the simulation time
//    final double curTime = core.SimClock.getTime();

//    if(curTime >= 1500 && curTime <= 4500) {
//      final double endTime = core.SimScenario.getInstance().getEndTime();
//      final double t = 1 / 2;
//
//      // Set the bounds based on the time
//      final double k = Math.sin( t * Math.PI );
//      final double hx = super.getMaxX() / 6;
//      final double hy = super.getMaxY() / 6;
//
//
//      return new Coord(
//              k * hx + ( rng.nextDouble() * hx ),
//              k * hy + ( rng.nextDouble() * hy ) );
//    }
      Coord c;
      do {
               c = new Coord(
                       rng.nextDouble() * super.getMaxX(),
                       rng.nextDouble() * super.getMaxY());
        } while ( pathIntersects( this.polygon, this.lastWaypoint, c ) );
    return c;
  }

//  @Override
//  public boolean isActive() {
//    final double curTime = core.SimClock.getTime();
//    //return true;//!(curTime >= 2500 && curTime <= 3500);
//      return isActive;
//  }


  @Override
  public double nextPathAvailable() {
    final double curTime = core.SimClock.getTime();
    if(goToLecture){
        goToLecture = false;
        //return curTime+1000.0;
        host.setOnTheWayToALecture(false);
        return 4000.0;
    }


    //if (curTime >= 2500 && curTime <= 3500) {
    //  return 3500;
    //}

    return curTime;
  }
  //==========================================================================//


  //==========================================================================//
  // API
  //==========================================================================//
  public MyProhibitedPolygonRwp( final Settings settings ) {
    super( settings );
    // Read the invert setting
    this.invert = settings.getBoolean( INVERT_SETTING, INVERT_DEFAULT );

    //this.setLocalMaxima();

    //System.out.println(super.getMaxX() + "   " + super.getMaxY());
    //System.out.println(localMaxX + "   " + localMaxY);
    //invert polygon too
    //simMap.mirror();
    //setBounds();
    //Coord offset = simMap.getMinBound().clone();
    //simMap.translate(-offset.getX(), -offset.getY());
  }

  public MyProhibitedPolygonRwp( final MyProhibitedPolygonRwp other ) {
    // Copy constructor will be used when settings up nodes. Only one
    // prototype node instance in a group is created using the Settings
    // passing constructor, the rest are replicated from the prototype.
    super( other );
    // Remember to copy any state defined in this class.
    this.invert = other.invert;
  }
  //==========================================================================//



  //==========================================================================//
  // Polygon Tools
  //==========================================================================//
  private static List <Coord> initializePolygon() {
      /*List <Coord> polygon = Arrays.asList(
            new Coord( 700, 700 ),
            new Coord( 730, 550 ),
            new Coord( 230, 450 ),
            new Coord( 200, 520 ),
            new Coord( 700, 700 )
      );*/

      List <Coord> polygon = Arrays.asList(
            new Coord( 700, -700 ),
            new Coord( 730, -550 ),
            new Coord( 230, -450 ),
            new Coord( 200, -520 ),
            new Coord( 700, -700 )
      );

      polygon = mirrorPolygon(polygon);
      Coord minBound = setBoundsOfPolygon(polygon, true);
      polygon = translatePolygon(polygon, -minBound.getX(), -minBound.getY());

      return polygon;
  }


  private static List <Coord> mirrorPolygon(List <Coord> pol) {
      for (Coord c : pol) {
        c.setLocation(c.getX(), -c.getY());
      }

      return pol;
  }

  private static Coord setBoundsOfPolygon(List <Coord> pol, boolean returnMinimum) {
      double minX, minY, maxX, maxY;
      minX = minY = Double.MAX_VALUE;
      maxX = maxY = -Double.MAX_VALUE;

      for (Coord c : pol) {

          if (c.getX() < minX) {
              minX = c.getX();
          }
          if (c.getX() > maxX) {
              maxX = c.getX();
          }
          if (c.getY() < minY) {
              minY = c.getY();
          }
          if (c.getY() > maxY) {
              maxY = c.getY();
          }
      }

      //Coord minBound = new Coord(minX, minY);
      //Coord maxBound = new Coord(maxX, maxY);

      if(returnMinimum)
          return new Coord(minX, minY);
      else
          return new Coord(maxX, maxY);
  }

  private static List <Coord> translatePolygon(List <Coord> pol, double dx, double dy) {
      for (Coord c : pol) {
          c.translate(dx, dy);
      }

        //minBound.translate(dx, dy);
        //maxBound.translate(dx, dy);
        //offset.translate(dx, dy);

      return pol;
  }


  private void setLocalMaxima() {
      Coord maxBound = setBoundsOfPolygon(polygon, false);
      this.localMaxX = (int) Math.round(maxBound.getX());
      this.localMaxY = (int) Math.round(maxBound.getY());
  }
  //==========================================================================//




  //==========================================================================//
  // Private - geometry
  //==========================================================================//
  private static boolean pathIntersects(
          final List <Coord> polygon,
          final Coord start,
          final Coord end ) {
    final int count = countIntersectedEdges( polygon, start, end );
    return ( count > 0 );
  }

  private static boolean isInside(
          final List <Coord> polygon,
          final Coord point ) {
    final int count = countIntersectedEdges( polygon, point,
            new Coord( -10,0 ) );
    return ( ( count % 2 ) != 0 );
  }

  private static boolean isOutside(
          final List <Coord> polygon,
          final Coord point ) {
    return !isInside( polygon, point );
  }

  private static int countIntersectedEdges(
          final List <Coord> polygon,
          final Coord start,
          final Coord end ) {
    int count = 0;
    for ( int i = 0; i < polygon.size() - 1; i++ ) {
      final Coord polyP1 = polygon.get( i );
      final Coord polyP2 = polygon.get( i + 1 );

      final Coord intersection = intersection( start, end, polyP1, polyP2 );
      if ( intersection == null ) continue;

      if ( isOnSegment( polyP1, polyP2, intersection )
              && isOnSegment( start, end, intersection ) ) {
        count++;
      }
    }
    return count;
  }

  private static boolean isOnSegment(
          final Coord L0,
          final Coord L1,
          final Coord point ) {
    final double crossProduct
            = ( point.getY() - L0.getY() ) * ( L1.getX() - L0.getX() )
            - ( point.getX() - L0.getX() ) * ( L1.getY() - L0.getY() );
    if ( Math.abs( crossProduct ) > 0.0000001 ) return false;

    final double dotProduct
            = ( point.getX() - L0.getX() ) * ( L1.getX() - L0.getX() )
            + ( point.getY() - L0.getY() ) * ( L1.getY() - L0.getY() );
    if ( dotProduct < 0 ) return false;

    final double squaredLength
            = ( L1.getX() - L0.getX() ) * ( L1.getX() - L0.getX() )
            + (L1.getY() - L0.getY() ) * (L1.getY() - L0.getY() );
    if ( dotProduct > squaredLength ) return false;

    return true;
  }

  private static Coord intersection(
          final Coord L0_p0,
          final Coord L0_p1,
          final Coord L1_p0,
          final Coord L1_p1 ) {
    final double[] p0 = getParams( L0_p0, L0_p1 );
    final double[] p1 = getParams( L1_p0, L1_p1 );
    final double D = p0[ 1 ] * p1[ 0 ] - p0[ 0 ] * p1[ 1 ];
    if ( D == 0.0 ) return null;

    final double x = ( p0[ 2 ] * p1[ 1 ] - p0[ 1 ] * p1[ 2 ] ) / D;
    final double y = ( p0[ 2 ] * p1[ 0 ] - p0[ 0 ] * p1[ 2 ] ) / D;

    return new Coord( x, y );
  }

  private static double[] getParams(
          final Coord c0,
          final Coord c1 ) {
    final double A = c0.getY() - c1.getY();
    final double B = c0.getX() - c1.getX();
    final double C = c0.getX() * c1.getY() - c0.getY() * c1.getX();
    return new double[] { A, B, C };
  }
  //==========================================================================//
}
