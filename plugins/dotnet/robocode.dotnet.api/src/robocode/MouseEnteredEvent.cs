/**
 * Copyright (c) 2001-2017 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
using System;
using net.sf.robocode.nio;
using net.sf.robocode.peer;
using net.sf.robocode.serialization;
using Robocode.RobotInterfaces;

namespace Robocode
{
    /// <summary>
    /// A MouseEnteredEvent is sent to <see cref="Robot.OnMouseEntered(MouseEvent)"/>
    /// when the mouse has entered the battle view.
    /// <seealso cref="MouseClickedEvent"/>
    /// <seealso cref="MousePressedEvent"/>
    /// <seealso cref="MouseReleasedEvent"/>
    /// <seealso cref="MouseExitedEvent"/>
    /// <seealso cref="MouseMovedEvent"/>
    /// <seealso cref="MouseDraggedEvent"/>
    /// <seealso cref="MouseWheelMovedEvent"/>
    /// </summary>
    [Serializable]
    public sealed class MouseEnteredEvent : MouseEvent
    {
        private const int DEFAULT_PRIORITY = 98;

        /// <summary>
        /// Called by the game to create a new MouseDraggedEvent.
        /// </summary>
        public MouseEnteredEvent(int button, int clickCount, int x, int y, int id, int modifiersEx, long when)
            : base(button, clickCount, x, y, id, modifiersEx, when)
        {
        }

        internal override int DefaultPriority
        {
            get { return DEFAULT_PRIORITY; }
        }

        internal override void Dispatch(IBasicRobot robot, IRobotStaticsN statics, IGraphics graphics)
        {
            if (statics.IsInteractiveRobot())
            {
                IInteractiveEvents listener = ((IInteractiveRobot) robot).GetInteractiveEventListener();

                if (listener != null)
                {
                    listener.OnMouseEntered(this);
                }
            }
        }

        internal override byte SerializationType
        {
            get { return RbSerializerN.MouseEnteredEvent_TYPE; }
        }

        private static ISerializableHelperN createHiddenSerializer()
        {
            return new SerializableHelper();
        }

        private class SerializableHelper : ISerializableHelperN
        {
            public int sizeOf(RbSerializerN serializer, object objec)
            {
                return RbSerializerN.SIZEOF_TYPEINFO + 6*RbSerializerN.SIZEOF_INT + RbSerializerN.SIZEOF_LONG;
            }

            public void serialize(RbSerializerN serializer, ByteBuffer buffer, object objec)
            {
                var obj = (MouseEnteredEvent)objec;

                serializer.serialize(buffer, obj.Button);
                serializer.serialize(buffer, obj.ClickCount);
                serializer.serialize(buffer, obj.X);
                serializer.serialize(buffer, obj.Y);
                serializer.serialize(buffer, obj.ID);
                serializer.serialize(buffer, obj.ModifiersEx);
                serializer.serialize(buffer, obj.When);
            }

            public object deserialize(RbSerializerN serializer, ByteBuffer buffer)
            {
                int button = buffer.getInt();
                int clickCount = buffer.getInt();
                int x = buffer.getInt();
                int y = buffer.getInt();
                int id = buffer.getInt();
                int modifiersEx = buffer.getInt();
                long when = buffer.getLong();

                return new MouseEnteredEvent(button, clickCount, x, y, id, modifiersEx, when);
            }
        }
    }
}

//doc