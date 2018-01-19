package robocode.naval.Components.RadarComponents;

import net.sf.robocode.security.IHiddenComponentHelper;
import net.sf.robocode.serialization.ISerializableHelper;
import net.sf.robocode.serialization.RbSerializer;
import robocode.naval.Components.ComponentBase;
import robocode.naval.RadarType;

import java.awt.geom.Arc2D;
import java.nio.ByteBuffer;

/**
 * @author Thales B.V. /Werner Struis (contributor naval)
 */
public class LongRangeRadar extends RadarComponent {

    public LongRangeRadar() {
        super(RadarType.LONG_RANGE);
    }


    public byte getSerializeType(){
        return RbSerializer.LongRangeRadar_TYPE;
    }

    /**
     * Creates a hidden radar component helper for accessing hidden methods on this object.
     *
     * @return a hidden radar component helper.
     */
    // this method is invisible on RobotAPI
    static IHiddenComponentHelper createHiddenHelper() {
        return new HiddenRadarHelper();
    }

    /**
     * Creates a hidden radar component helper for accessing hidden methods on this object.
     *
     * @return a hidden radar component helper.
     */
    // this class is invisible on RobotAPI
    static ISerializableHelper createHiddenSerializer() {
        return new HiddenRadarHelper();
    }
    
    // ======================================================================
    //  Serialize for this component.
    // ======================================================================

    // this class is invisible on RobotAPI
    private static class HiddenRadarHelper extends HiddenComponentHelper {

        /**
         * {@inheritDoc}
         */
        public void updateSub(ComponentBase component) {
            // Update this object...
        }

        /**
         * {@inheritDoc}
         */
        protected int getSize(RbSerializer serializer, Object object) {
            int size = RbSerializer.SIZEOF_INT;
            size += 6 * RbSerializer.SIZEOF_DOUBLE;
            return size;
        }

        /**
         * {@inheritDoc}
         */
        protected void serializeSub(RbSerializer serializer, ByteBuffer buffer, Object object) {
            RadarComponent radar = (RadarComponent) object;

            serializer.serialize(buffer, radar.scanArc.x);
            serializer.serialize(buffer, radar.scanArc.y);
            serializer.serialize(buffer, radar.scanArc.width);
            serializer.serialize(buffer, radar.scanArc.height);
            serializer.serialize(buffer, radar.scanArc.start);
            serializer.serialize(buffer, radar.scanArc.extent);
            serializer.serialize(buffer, radar.scanArc.getArcType());
        }

        /**
         * {@inheritDoc}
         */
        protected void deserializeSub(RbSerializer serializer, ByteBuffer buffer, Object component) {
            RadarComponent radar = (RadarComponent) component;

            radar.scanArc = new Arc2D.Double(
                    serializer.deserializeDouble(buffer),
                    serializer.deserializeDouble(buffer),
                    serializer.deserializeDouble(buffer),
                    serializer.deserializeDouble(buffer),
                    serializer.deserializeDouble(buffer),
                    serializer.deserializeDouble(buffer),
                    serializer.deserializeInt(buffer));
        }

        /**
         * {@inheritDoc}
         */
        protected ComponentBase getObject() {
            return new LongRangeRadar();
        }


    }
}
