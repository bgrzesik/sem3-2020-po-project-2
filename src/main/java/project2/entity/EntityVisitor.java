package project2.entity;

public interface EntityVisitor {

    default void visitPlayer(PlayerEntity player) {
    }

    default void visitEnemy(EnemyEntity enemy) {
    }

    default void visitCherry(CherryEntity cherry) {
    }

    default void visitPoint(PointEntity point) {
    }

    default void visitMap(MapEntity map) {
    }

}
