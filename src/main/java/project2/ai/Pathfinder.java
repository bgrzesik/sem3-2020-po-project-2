package project2.ai;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import project2.entity.EnemyEntity;
import project2.entity.Entity;
import project2.map.GameMap;
import project2.system.EnemySystem;
import project2.system.PhysicsSystem;

import java.util.*;
import java.util.function.Predicate;

public class Pathfinder {
    public static final GridPoint2[] NEIGHBORS = {
            new GridPoint2(0, 1), // N
            new GridPoint2(1, 0), // E
            new GridPoint2(0, -1), // S
            new GridPoint2(-1, 0), // W
    };

    PathVertex[][] vertices;
    private GameMap map;
    private Vector2 target;
    private PhysicsSystem physicsSystem;
    private boolean foundPaths = false;

    public Pathfinder(GameMap map, Vector2 target, PhysicsSystem physicsSystem) {
        this.vertices = new PathVertex[map.getSizeX()][map.getSizeY()];
        this.map = map;
        this.target = target;
        this.physicsSystem = physicsSystem;
    }

    public List<Vector2> getPathFrom(Vector2 from, float raySize, Predicate<Entity> predicate) {
        if (!this.foundPaths) {
            this.pathFind();
        }

        List<Vector2> path = new ArrayList<>();

        PathVertex vertex = this.vertices[MathUtils.round(from.x)][MathUtils.round(from.y)];

        path.add(from);

        while (vertex != null && vertex.parent != null) {
            vertex = this.vertices[vertex.parent.x][vertex.parent.y];
            path.add(new Vector2(vertex.pos.x, vertex.pos.y));
        }

        if (path.size() > 1) {
            path.remove(path.size() - 1);
            path.add(target);
        }

        int i = 0;
        while (i < path.size() - 2) {
            Vector2 v1 = path.get(i);
            Vector2 v2 = path.get(i + 2);

            List<Entity> entities = physicsSystem.rayCast(v1, v2, raySize);

            if (entities.stream().anyMatch(predicate)) {
                i++;
            } else {
                path.remove(i + 1);
            }
        }


        return path;
    }

    private void pathFind() {
        Queue<GridPoint2> queue = new ArrayDeque<>();
        GridPoint2 point = new GridPoint2(MathUtils.round(target.x),
                                          MathUtils.round(target.y));

        this.vertices[point.x][point.y] = new PathVertex(point);
        this.vertices[point.x][point.y].dist = 0;
        queue.add(point);

        while (!queue.isEmpty()) {
            point = queue.poll();

            PathVertex vertex = this.vertices[point.x][point.y];
            vertex.visited = true;

            for (GridPoint2 neighbor : NEIGHBORS) {
                GridPoint2 n = new GridPoint2(point).add(neighbor);

                if (n.x <= 0 || n.x > map.getSizeX()) {
                    continue;
                }
                if (n.y <= 0 || n.y > map.getSizeX()) {
                    continue;
                }
                if (this.map.isWall(n)) {
                    continue;
                }

                if (this.vertices[n.x][n.y] == null) {
                    this.vertices[n.x][n.y] = new PathVertex(n);
                }

                PathVertex vn = this.vertices[n.x][n.y];

                if (vn.visited) {
                    continue;
                }

                if (vn.dist > vertex.dist + neighbor.dst(0, 0)) {
                    vn.dist = vertex.dist + neighbor.dst(0, 0);
                    vn.parent = point;
                    queue.add(n);
                }
            }
        }

        this.foundPaths = true;
    }


    private static class PathVertex {
        private GridPoint2 parent = null;

        private final GridPoint2 pos;
        private float dist = Float.MAX_VALUE;
        private boolean visited = false;

        public PathVertex(GridPoint2 pos) {
            this.pos = pos;
        }
    }


}
